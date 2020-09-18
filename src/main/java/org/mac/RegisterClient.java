package org.mac;

import java.util.UUID;

/**
 * 服务注册组件，提供给各个服务使用
 */
public class RegisterClient {

    //服务实例id
    private String serviceInstanceId;

    public RegisterClient() {
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-","");
    }

    public void start(){
        new RegisterClientWorker(serviceInstanceId).start();
    }

    /**
     * register-client工作线程类
     * 两件事情
     * 1、服务注册 2、心跳发送
     */
    private class RegisterClientWorker extends Thread{

        private static final String SERVICE_NAME = "inventory_service";
        private static final String HOST_NAME = "inventory01";
        private static final String IP = "192.168.31.154";
        private static final int PORT = 7000;

        //服务实例id
        private String serviceInstanceId;

        /**
         * http组件
         */
        private HttpSender httpSender;

        private Boolean finishedRegister;

        public RegisterClientWorker(String serviceInstanceId) {
            this.httpSender = new HttpSender();
            this.serviceInstanceId = serviceInstanceId;
            this.finishedRegister = false;
        }

        @Override
        public void run() {
            if (!finishedRegister){
                //构建服务注册request
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setServiceName(SERVICE_NAME);
                registerRequest.setServiceInstanceId(serviceInstanceId);
                registerRequest.setHostName(HOST_NAME);
                registerRequest.setIp(IP);
                registerRequest.setPort(PORT);

                /**
                 * 发送 注册 请求
                 */
                RegisterResponse response = httpSender.register(registerRequest);

                if (RegisterResponse.SUCCESS.equals(response.getStatus()))
                    this.finishedRegister = true;
                else return;
            }

            /**
             * 如果已经注册，则发送心跳
             */
            if (finishedRegister){
                //构建心跳请求
                HeartBreakRequest request = new HeartBreakRequest(serviceInstanceId);
                HeartBreakResponse response = null ;
                while (true){
                    try {
                        response = httpSender.heartBeatSend(request);
                        System.out.println("发送心跳的结果:"+response.getStatus());
                        Thread.sleep(30*1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}

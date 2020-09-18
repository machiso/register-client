package org.mac;

import java.util.UUID;

/**
 * 服务注册组件，提供给各个服务使用
 */
public class RegisterClient {
    private static final String SERVICE_NAME = "inventory_service";
    private static final String HOST_NAME = "inventory01";
    private static final String IP = "192.168.31.154";
    private static final int PORT = 7000;
    //服务实例id
    private String serviceInstanceId;
    //http组件
    private HttpSender httpSender;

    //发送心跳实例组件
    private HeartBeatWorker heartBeatWorker;

    //定时拉取服务组件
    private ClientCachedServiceRegistry registry;

    //服务实例是否运行
    //使用volatile修饰，当服务下线时，可以让定时拉取register server的线程感知到，马上停止
    //利用volatile在多线程之间的可见性
    private volatile Boolean isRunning;

    public RegisterClient(String serviceInstanceId) {
        this.isRunning = false;
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-","");
        this.httpSender = new HttpSender();
        this.serviceInstanceId = serviceInstanceId;
        this.heartBeatWorker = new HeartBeatWorker();
        this.registry = new ClientCachedServiceRegistry(this,httpSender);
    }

    //判断register client是否运行
    public Boolean isRunning() {
        return isRunning;
    }

    //入口，启动register-client组件
    //这里其实服务注册和发送心跳是两个组件，分别对应两个后台线程来完成
    //发送心跳要等到服务注册完成之后才可以做
    public void start(){
        try {
            //服务注册组件
            ResigterWorker worker = new ResigterWorker();
            //启动服务注册的线程
            worker.start();
            // join方法，要等运行结束之后才会继续执行下面的代码，因此当服务注册完成才会执行发送心跳的代码
            worker.join();

            heartBeatWorker.start();

            //初始化客户端定时拉取服务注册实例组件
            registry.init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * register-client销毁
     * 1、isRunning变为false，则HeartBeatWorker立即停止发送心跳，volatile修饰
     * 2、定时拉取服务注册表的线程要销毁
     */
    public void shutdown(){
        this.isRunning = false;
        //interrupt方法可以是sleep的方法立即苏醒
        this.heartBeatWorker.interrupt();
        this.registry.destroy();
    }

    //负责服务注册的线程
    class ResigterWorker extends Thread{
        @Override
        public void run() {
            //构建服务注册request
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setServiceName(SERVICE_NAME);
            registerRequest.setServiceInstanceId(serviceInstanceId);
            registerRequest.setHostName(HOST_NAME);
            registerRequest.setIp(IP);
            registerRequest.setPort(PORT);

            //发送注册请求
            RegisterResponse response = httpSender.register(registerRequest);

            if (response != null && response.getStatus() == RegisterResponse.SUCCESS){
                isRunning = true;
            }
        }
    }

    //负责发送心跳实例的组件
    class HeartBeatWorker extends Thread{
        @Override
        public void run() {
            try{
                //构建心跳请求
                HeartBreakRequest request = new HeartBreakRequest(serviceInstanceId);
                HeartBreakResponse response = null ;
                while (isRunning){
                    try {
                        response = httpSender.heartBeatSend(request);
                        System.out.println("发送心跳的结果:"+response.getStatus());
                        Thread.sleep(30*1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

package org.mac;

/**
 * http通信组件
 */
public class HttpSender {
    /**
     * 服务注册
     * @param registerRequest
     * @return
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        /**
         * 模拟发送服务注册请求
         */
        System.out.println("服务"+registerRequest.getServiceInstanceId()+"注册成功");
        RegisterResponse response = new RegisterResponse();
        response.setStatus(RegisterResponse.SUCCESS);

        return response;
    }

    /**
     * 发送心跳请求
     * @param request
     * @return
     */
    public HeartBreakResponse heartBeatSend(HeartBreakRequest request) {
        HeartBreakResponse response = new HeartBreakResponse();
        response.setStatus(HeartBreakResponse.SUCCESS);
        return response;
    }
}

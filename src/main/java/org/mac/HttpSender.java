package org.mac;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 拉取服务注册中心实例
     * @return
     */
    public Map<String, Map<String, ServiceInstance>> fetchServiceRegistry() {
        Map<String, Map<String, ServiceInstance>> registry =
                new HashMap<String, Map<String, ServiceInstance>>();

        //模拟拉取服务注册实例
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setHostName("finance-service-01");
        serviceInstance.setIp("192.168.31.1207");
        serviceInstance.setPort(9000);
        serviceInstance.setServiceInstanceId("FINANCE-SERVICE-192.168.31.207:9000");
        serviceInstance.setServiceName("FINANCE-SERVICE");

        Map<String, ServiceInstance> serviceInstances = new HashMap<String, ServiceInstance>();
        serviceInstances.put("FINANCE-SERVICE-192.168.31.207:9000", serviceInstance);

        registry.put("FINANCE-SERVICE", serviceInstances);

        System.out.println("拉取注册表：" + registry);

        return registry;
    }
}

package org.mac;

/**
 * 发送心跳请求
 */
public class HeartBreakRequest {
    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    public HeartBreakRequest(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }
}

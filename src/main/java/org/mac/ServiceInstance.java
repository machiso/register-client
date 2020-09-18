package org.mac;


/**
 * 服务实例
 */
public class ServiceInstance {

    private static final Long NOT_ALIVE_PERIOD = 9 * 1000L;
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * 契约信息
     */
    private Lease lease;

    public ServiceInstance() {
        this.lease = new Lease();
    }

    public void renew() {
        this.lease.renew();
    }

    /**
     * 判断实例是否存活
     * @return
     */
    public boolean isAlive() {
        return this.lease.isAlive();
    }

    /**
     * 摘除宕机的服务实例
     */
    public void remove() {

    }


    /**
     * 内部类，维护实例对应的契约信息
     * 包括心跳信息，续约信息等
     */
    private class Lease {
        private Long latestHeartbeatTime = System.currentTimeMillis();

        public Long getLatestHeartbeatTime() {
            return latestHeartbeatTime;
        }

        public void setLatestHeartbeatTime(Long latestHeartbeatTime) {
            this.latestHeartbeatTime = latestHeartbeatTime;
        }

        /**
         * 服务续约
         */
        public void renew() {
            this.latestHeartbeatTime = System.currentTimeMillis();
            System.out.println("服务实例【" + serviceInstanceId + "】，进行续约：" + latestHeartbeatTime);
        }

        /**
         * 判断服务实例是否还存活
         * @return
         */
        public boolean isAlive() {
            Long currentTime = System.currentTimeMillis();
            if(currentTime - latestHeartbeatTime > NOT_ALIVE_PERIOD) {
                System.out.println("服务实例【" + serviceInstanceId + "】，不再存活");
                return false;
            }
            System.out.println("服务实例【" + serviceInstanceId + "】，保持存活");
            return true;
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", hostName='" + hostName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", lease=" + lease +
                '}';
    }
}

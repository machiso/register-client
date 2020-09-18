package org.mac;


import java.util.HashMap;
import java.util.Map;

/**
 * 定时从服务注册中心拉取实例的组件
 * 客户端缓存在本地的服务实例列表
 * 30s定时从register-server拉取一次，更新本地缓存
 */
public class ClientCachedServiceRegistry {

    //客户端缓存的服务注册实例列表
    public Map<String,Map<String,ServiceInstance>> registry = new HashMap<String,Map<String,ServiceInstance>>();

    //定时任务拉取时间
    private static final Long SERVICE_REGISTRY_FETCH_INTERVAL = 30*1000l;

    //负责定时更新注册表的后台线程daemon
    private Daemon daemon;

    private RegisterClient registerClient;

    private HttpSender httpSender;

    public ClientCachedServiceRegistry(RegisterClient registerClient, HttpSender httpSender) {
        this.registerClient = registerClient;
        this.httpSender = httpSender;
        this.daemon =new Daemon();
    }


    //初始化组件
    public void init(){
        daemon.start();
    }

    //销毁组件
    public void destroy(){
        daemon.interrupt();
    }

    class Daemon extends Thread{
        @Override
        public void run() {
            //只要客户端还在运行，就一直定时拉取
            while (registerClient.isRunning()){
                try {
                    registry = httpSender.fetchServiceRegistry();
                    Thread.sleep(SERVICE_REGISTRY_FETCH_INTERVAL);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

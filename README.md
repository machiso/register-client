# register-client
服务注册组件,主要完成服务注册以及心跳发送组件，定时拉取server端服务注册表组件
它并不是一个需要独立部署的服务，而是以jar包的方式引入项目中，则项目就可以向服务注册中心server端完成服务的注册。类似于springcloud中的DiscoveryClient一样。


1、服务注册组件
2、心跳发送组件
3、定时拉取server服务注册表




涉及到的并发编程的知识点：
1、服务下线的时候，需要将isRunning用volatile修饰，可以在多线程之间的可见性，立即让如定时拉取实例，发送心跳等组件停止运行
2、join的使用
3、interrupt的使用，结合sleep方法

###一、TPS
    Transactions Per Second（每秒传输的事物处理个数），即服务器每秒处理的事务数。
    
    TPS包括一条消息入和一条消息出，加上一次用户数据库访问。（业务TPS = CAPS × 每个呼叫平均TPS）
###二、QPS
    每秒查询率QPS是对一个特定的查询服务器在规定时间内所处理流量多少的衡量标准，
    
    在因特网上，作为域名系统服务器的机器的性能经常用每秒查询率来衡量。
###三、bps
    在计算机网络或者是网络运营商中，一般，宽带速率的单位用bps(或b/s)表示；bps表示比特每秒即表示每秒钟传输多少位信息，是bit per second的缩写。
    
###四、RPS
     Requests Per Second的缩写，每秒能处理的请求数目。
###五、总结
     1、Tps即每秒处理事务数，包括了
     
     1）用户请求服务器
     
     2）服务器自己的内部处理
     
     3）服务器返回给用户
     
     这三个过程，每秒能够完成N个这三个过程，Tps也就是N；
     
     2、Qps基本类似于Tps，但是不同的是，对于一个页面的一次访问，形成一个Tps；但一次页面请求，可能产生多次对服务器的请求，服务器对这些请求，就可计入“Qps”之中。
     
###六、SpringBoot健康检查实现原理 

    https://www.cnblogs.com/zhixiang-org-cn/p/11645753.html
    这个人有点东西可以看：
    https://www.cnblogs.com/zhixiang-org-cn/default.html?page=5
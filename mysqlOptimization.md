###一、概述
+ MySQL数据库是常见的两个瓶颈是CPU和I/O的瓶颈。
    + CPU在饱和的时候一般发生在数据装入内存或从磁盘上读取数据时候。
    + 磁盘I/O瓶颈发生在装入数据远大于内存容量的时候，如果应用分布在网络上，那么查询量相当大的时候那么平瓶颈就会出现在网络上，我们可以用mpstat, iostat, sar和vmstat来查看系统的性能状态。
    + 除了服务器硬件的性能瓶颈，对于MySQL系统本身，我们可以使用工具来优化数据库的性能，通常有三种：使用索引，使用EXPLAIN分析查询以及调整MySQL的内部配置。
###二、查询与索引优化分析
在优化MySQL时，通常需要对数据库进行分析，常见的分析手段有慢查询日志，EXPLAIN 分析查询，profiling分析以及show命令查询系统状态及系统变量，
通过定位分析性能的瓶颈，才能更好的优化数据库系统的性能。
####2.1性能瓶颈定位
+ show命令
        
        我们可以通过show命令查看MySQL状态及变量，找到系统的瓶颈：
        
        Mysql> show status ——显示状态信息（扩展show status like ‘XXX’）
        
        Mysql> show variables ——显示系统变量（扩展show variables like ‘XXX’）
        
        Mysql> show innodb status ——显示InnoDB存储引擎的状态
        
        Mysql> show processlist ——查看当前SQL执行，包括执行状态、是否锁表等
        
        Shell> mysqladmin variables -u username -p password——显示系统变量
        
        Shell> mysqladmin extended-status -u username -p password——显示状态信息
    
        查看状态变量及帮助：
        Shell> 
        mysqld –verbose –help [|more #逐行显示]
        比较全的Show命令的使用可参考： http://blog.phpbean.com/a.cn/18/
 + 慢查询日志   
      慢查询日志开启：
      
      方式一：
      
       在配置文件my.cnf或my.ini中在[mysqld]一行下面加入两个配置参数
        
       log-slow-queries=/data/mysqldata/slow-query.log

       long_query_time=2

       注：log-slow-queries参数为慢查询日志存放的位置，一般这个目录要有mysql的运行帐号的可写权限，一般都将这个目录设置为mysql的数据存放目录；

       long_query_time=2中的2表示查询超过两秒才记录；

       在my.cnf或者my.ini中添加log-queries-not-using-indexes参数，表示记录下没有使用索引的查询。

       log-slow-queries=/data/mysqldata/slow-query.log

       long_query_time=10

       log-queries-not-using-indexes 
      方式二：
      
       使用命令行：
       查看慢查询日志是否开启：show variables like “slow%”；
       设置慢查询日志开启：set global slow_query_log=on
       查看long_query_time的值：show variables like “long%” 大于这个时间的会计入慢查询
       show variables like '%timeout%';
       show OPEN TABLES where In_use > 0;
       show processlist;
       show full processlist;
       SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS;
       SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS;
       show status like '%lock%';
       kill 2;
       select * from information_schema.innodb_trx
       select * from information_schema.innodb_locks
       select * from information_schema.innodb_lock_waits  
       然后可以去日志文件中查看，也可以用MySQL自带的mysqldumpslow分析
+ explain 

      explain extended  (sql)
      show warnings
       


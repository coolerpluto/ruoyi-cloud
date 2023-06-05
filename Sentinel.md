#参考
从ruoyi-gateway中学习sentinel的用法
https://blog.csdn.net/aofengdaxia/article/details/129294025

ruoyi-cloud—若依微服务springcloud安装Sentinel 实现熔断和降级
https://blog.csdn.net/zhaolulu916/article/details/126955446

官网启动项配置说明
https://sentinelguard.io/zh-cn/docs/general-configuration.html

Sentinel 的安装，登录帐号密码设置，以及与springCloud中的使用，使用Nacos实现Sentinel的半自动持久化配置
https://blog.csdn.net/nece001/article/details/107082394

#引入sentinel的依赖，
    我们可以在pom.xml中添加如下依赖：
        <!-- SpringCloud Alibaba Sentinel -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>

        <!-- SpringCloud Alibaba Sentinel Gateway -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
        </dependency>

        <!-- Sentinel Datasource Nacos -->
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
#配置文件
    spring:
      cloud:
        sentinel:
          # 取消控制台懒加载
          eager: true
          transport:
            # 控制台地址
            dashboard: 127.0.0.1:8718
          # nacos配置持久化
          datasource:
            ds1:
              nacos:
                server-addr: 127.0.0.1:8848
                dataId: sentinel-gateway
                groupId: DEFAULT_GROUP
                data-type: json
                rule-type: flow

#sentinel规则
    [
        {
        "resource": "/api/**",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
        }
    ]
规则的具体含义如下：

resource：资源名称，可以是网关中的路由名称或者用户自定义的API分组名称。

limitApp：流控针对的调用来源，若为 default 则不区分调用来源。否则，若调用来源不是 default，则只有调用来源是 limitApp 的流量才会进行流控。

grade：限流阈值类型，QPS 或线程数。具体的值为 0 或 1，分别对应 QPS 和线程数。

count：限流阈值。

strategy：调用关系限流策略，目前只支持直接、关联和链路三种模式。具体的值为 0、1、2，分别对应直接、关联和链路。

controlBehavior：流控效果（直接拒绝、Warm Up、匀速排队）。具体的值为 0、1、2，分别对应直接拒绝、Warm Up、匀速排队。

clusterMode：是否为集群模式。

#启动Dashboard
1、下载最新的dashboard, 去github下载合适的dashboard版本 https://github.com/alibaba/Sentinel/releases
2、执行下面命令启动dashboard

    java 
        -Dserver.port=8718 
        -Dcsp.sentinel.dashboard.server=localhost:8718 
        -Dproject.name=sentinel-dashboard 
        -jar sentinel-dashboard-1.8.0.jar

3、访问dashboard http://localhost:8718,默认的账户和密码都是 sentinel

##启动参数说明
Sentinel 提供如下的配置方式：

JVM -D 参数方式
properties 文件方式（1.7.0 版本开始支持）
其中，project.name 参数只能通过 JVM -D 参数方式配置（since 1.8.0 取消该限制），其它参数支持所有的配置方式。

优先级顺序：JVM -D 参数的优先级最高。若 properties 和 JVM 参数中有相同项的配置，以 JVM 参数配置的为准。

用户可以通过 -Dcsp.sentinel.config.file 参数配置 properties 文件的路径，支持 classpath 路径配置（如 classpath:sentinel.properties）。

默认 Sentinel 会尝试从 classpath:sentinel.properties 文件读取配置，读取编码默认为 UTF-8。


    -Dserver.port=8718用于指定Sentinel控制台端口为8718

###控制台配置项

|名称|含义|类型|默认值|是否必需|备注|
|----|----|----|----|----|----|
|server.port|用于指定 Sentinel 控制台端口|String|null|否||
|sentinel.dashboard.auth.username|用于指定控制台的登录用户名|String|sentinel |否||
|sentinel.dashboard.auth.password|用于指定控制台的登录密码 控制台端口为|String|sentinel |否||
|server.servlet.session.timeout|用于指定 Spring Boot 服务端 session 的过期时间|int|7200 |否|表示 7200 秒；60m 表示 60 分钟，默认为 30 分钟|


###sentinel-core 的配置项 基础配置项

|名称|含义|类型|默认值|是否必需|备注|
|----|----|----|----|----|----|
|project.name|指定应用的名称|String|null|否||
|csp.sentinel.app.type|指定应用的类型|	int|0 (APP_TYPE_COMMON)|否|1.6.0 引入|
|csp.sentinel.metric.file.single.size|单个监控日志文件的大小|long|52428800 (50MB)|否|
|csp.sentinel.metric.file.total.count|监控日志文件的总数上限|int|6|否|
|csp.sentinel.statistic.max.rt|最大的有效响应时长（ms），超出此值则按照此值记录|int|4900|否|1.4.1 引入|
|csp.sentinel.spi.classloader|SPI 加载时使用的 ClassLoader，默认为给定类的 ClassLoader|String|default|否|若配置 context 则使用 thread context ClassLoader。1.7.0 引入|

其中 project.name 项用于指定应用名（appName）。若未指定，则默认解析 main 函数的类名作为应用名。实际项目使用中建议手动指定应用名。

###日志相关配置项

|名称|含义|类型|默认值|是否必需|备注|
|----|----|----|----|----|----|
|csp.sentinel.log.dir|	Sentinel 日志文件目录|String|	${user.home}/logs/csp/|否|1.3.0 引入|
|csp.sentinel.log.use.pid|	日志文件名中是否加入进程号，用于单机部署多个应用的情况|boolean|false|否|1.3.0 引入|
|csp.sentinel.log.output.type|	Record 日志输出的类型，file 代表输出至文件，console 代表输出至终端|String|file|否|1.6.2 引入|

注意：若需要在单台机器上运行相同服务的多个实例，则需要加入 -Dcsp.sentinel.log.use.pid=true 来保证不同实例日志的独立性。

###sentinel-transport-common 的配置项

|名称|含义|类型|默认值|是否必需|备注|
|----|----|----|----|----|----|
|csp.sentinel.dashboard.server|控制台的地址，指定控制台后客户端会自动向该地址发送心跳包。地址格式为：hostIp:port|String|null|是|
|csp.sentinel.heartbeat.interval.ms|心跳包发送周期，单位毫秒|long|null|非必需，若不进行配置，则会从相应的 HeartbeatSender 中提取默认值|
|csp.sentinel.api.port|本地启动 HTTP API Server 的端口号|int|8719|否|
|csp.sentinel.heartbeat.client.ip|指定心跳包中本机的 IP|String|-|若不指定则通过 HostNameUtil 解析；该配置项多用于多网卡环境|

注：csp.sentinel.api.port 可不提供，默认为 8719，若端口冲突会自动向下探测可用的端口。


##项目启动样例：

    java 
        -Dserver.port=8480 
        -Dcsp.sentinel.dashboard.server=localhost:8480 
        -Dproject.name=sentinel-dashboard 
        -Dsentinel.dashboard.auth.username=sentinel 
        -Dsentinel.dashboard.auth.password=123456 
        -jar sentinel-dashboard-1.8.6.jar

    java 
        -Dserver.port=8718 
        -Dcsp.sentinel.dashboard.server=localhost:8718 
        -Dproject.name=sentinel-dashboard 
        -Dcsp.sentinel.api.port=8719 
        -jar D:\sentinel\sentinel-dashboard-1.8.6.jar

#问题
应用在docker中运行时，无法和单独运行的 Sentinel 进行交互，应用单独在本地运行时才可以进行交互
方案：配置 docker 网络
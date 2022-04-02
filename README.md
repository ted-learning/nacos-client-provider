# nacos-client-provider

https://nacos.io/zh-cn/docs/what-is-nacos.html








安装Nacos
https://github.com/alibaba/nacos/releases




启动服务器
Linux/Unix/Mac
启动命令(standalone代表着单机模式运行，非集群模式):


sh startup.sh -m standalone


如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：


bash startup.sh -m standalone


Windows
启动命令(standalone代表着单机模式运行，非集群模式):


startup.cmd -m standalone

关闭服务器
#Linux/Unix/Mac
sh shutdown.sh

#Windows
shutdown.cmd
或者双击shutdown.cmd运行文件。


Dashboard http://localhost:8848/nacos/#/login
Username: nacos
Password: nacos




启动配置管理
启动了 Nacos server 后，您就可以参考以下示例代码，为您的 Spring Cloud 应用启动 Nacos 配置管理服务了。完整示例代码请参考：nacos-spring-cloud-config-example
1. 添加依赖：
implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:2021.1")
注意：版本 2.1.x.RELEASE 对应的是 Spring Boot 2.1.x 版本。版本 2.0.x.RELEASE 对应的是 Spring Boot 2.0.x 版本，版本 1.5.x.RELEASE 对应的是 Spring Boot 1.5.x 版本。
2. 在 bootstrap.properties 中配置 Nacos server 的地址和应用名
spring:
  application:
    name: nacos-client-provider
  cloud:
    nacos:
      config:
        server-addr: "127.0.0.1:8848"
        file-extension: yaml
说明：之所以需要配置 spring.application.name ，是因为它是构成 Nacos 配置管理 dataId字段的一部分。
Note: 需要依赖org.springframework.cloud:spring-cloud-starter-bootstrap
在 Nacos Spring Cloud 中，dataId 的完整格式如下：
${prefix}-${spring.profiles.active}.${file-extension}
* prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
* spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
* file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。
4. 通过 Spring Cloud 原生注解 @RefreshScope 实现配置自动更新：
@RequestMapping("/")
@RestController
@RefreshScope
class HelloWorldController {


    @Value("\${word:world}")
    private lateinit var word: String


    @GetMapping("/hello")
    fun hello(): Map<String, String> {
        return mapOf("data" to "hello $word")
    }
}

5. 配置






1. 添加依赖：
implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2021.1")
注意：版本 2.1.x.RELEASE 对应的是 Spring Boot 2.1.x 版本。版本 2.0.x.RELEASE 对应的是 Spring Boot 2.0.x 版本，版本 1.5.x.RELEASE 对应的是 Spring Boot 1.5.x 版本。
更多版本对应关系参考：版本说明 Wiki
2. 配置服务提供者，从而服务提供者可以通过 Nacos 的服务注册发现功能将其服务注册到 Nacos server 上。
i. 在 application.properties 中配置 Nacos server 的地址：
spring:
  cloud:
    nacos:
      discovery:
        server-addr: "127.0.0.1:8848"
server:
  port: 0
ii. 通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能：
@EnableDiscoveryClient
@SpringBootApplication
class NacosClientProviderApplication


fun main(args: Array<String>) {
    runApplication<NacosClientProviderApplication>(*args)
}
3. 配置服务消费者，从而服务消费者可以通过 Nacos 的服务注册发现功能从 Nacos server 上获取到它要调用的服务。
i. 在 application.properties 中配置 Nacos server 的地址：
spring:
  cloud:
    nacos:
      discovery:
        server-addr: "127.0.0.1:8848"
server:
  port: 0
ii. 通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能。给 RestTemplate 实例添加 @LoadBalanced 注解，开启 @LoadBalanced 与 Ribbon 的集成：
测试未成功
@LoadBalanced
@Bean
fun restWithLoadBalanced(): RestTemplate {
    return RestTemplateBuilder()
        .rootUri("/nacos-client-provider")
        .build()
}





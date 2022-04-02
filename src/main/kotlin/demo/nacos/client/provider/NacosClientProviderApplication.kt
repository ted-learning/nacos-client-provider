package demo.nacos.client.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class NacosClientProviderApplication

fun main(args: Array<String>) {
    runApplication<NacosClientProviderApplication>(*args)
}

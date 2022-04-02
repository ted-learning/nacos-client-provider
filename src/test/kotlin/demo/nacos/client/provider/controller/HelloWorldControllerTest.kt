package demo.nacos.client.provider.controller

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@EnableDiscoveryClient
@SpringBootTest
internal class HelloWorldControllerTest {
    @Autowired
    lateinit var discoveryClient: DiscoveryClient

    @Test
    fun serviceUrl() {
        for (i in 1..100) {
            Thread.sleep(1000)
            try {
                val list = discoveryClient.getInstances("nacos-client-provider").map {
                    it.uri
                }
                val response = RestTemplate().getForObject<Map<String, String>>("${list.random()}/hello")
                Assertions.assertThat(response["data"]).isNotEmpty
                println("$i.succeed, size: ${list.size}, nodes: $list")
            } catch (e: Exception) {
                println("$i.failed")
            }
        }
    }
}
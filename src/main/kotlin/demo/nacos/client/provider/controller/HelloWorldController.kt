package demo.nacos.client.provider.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
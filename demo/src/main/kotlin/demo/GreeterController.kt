package demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("greeter")
class GreeterController {
    @Autowired
    private lateinit var greeter: Greeter

    @GetMapping("/hello/{name}")
    fun hello(@PathVariable("name") name: String): HelloResponse {
        return HelloResponse("Hello $name")
    }

    @PostMapping("/hey")
    fun heyByPost(@RequestBody request: HelloRequest): HelloResponse {
        return HelloResponse("Hey ${request.name}")
    }

    @GetMapping("/hello/service/{name}")
    fun helloService(@PathVariable("name") name: String): HelloResponse {
        val message = greeter.sayHello(name)
        return HelloResponse(message)
    }
}

package io.squer.view

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import reactor.core.publisher.Mono

@Controller
class TestController {
    @Get("/test")
    fun testEndpoint(): Mono<String> {
        return Mono.just("test");
    }
}
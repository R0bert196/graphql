package com.graphql.first.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class HelloWorldResolver {
    @QueryMapping
    fun helloWorld(): String {
        return "Hello World!"
    }

    @QueryMapping
    fun greet(@Argument name: String): String {
        return "Hello $name";
    }
}

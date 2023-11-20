package com.graphql.first.resolver

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


@Controller
class HelloWorldResolver {

    @QueryMapping
    fun test(@Argument date: LocalDate,
             @Argument bornAt: OffsetDateTime,
             @Argument phoneNumber: String,
             @Argument email: String)
    : String {
        return "date: $date, bor at: $bornAt, phone number: $phoneNumber, email: $email"
    }

    @QueryMapping
    fun helloWorld(): String {
        return "Hello World!"
    }

    @QueryMapping
    fun greet(@Argument name: String): String {
        return "Hello $name"
    }

    @QueryMapping
    fun getRandomNumbers(): List<Int> {
        return listOf(1, 2, 3)
    }

    @QueryMapping
    fun getEvent(): Event {
        return  Event(
            id = UUID.randomUUID(),
            eventType = "TESTING"
        )
    }
}

data class Event(
    private val id: UUID,
    private val eventType: String
)

package com.graphql.first.resolver

import com.graphql.first.config.GraphqlConfig
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.context.annotation.Import
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.test.context.ActiveProfiles

@Import(GraphqlConfig::class)
@ActiveProfiles("test")
@GraphQlTest(HelloWorldResolver::class)
class HelloWorldResolverTest(
    private val grapgQlTester: GraphQlTester
) : DescribeSpec({

    describe("Hello World Queries testcases") {

        it("should be able to execute helloWorld query") {

            // language=GraphQl
            val HELLO_WORLD_QUERY = """
                
                query {
                    helloWorld
                 }
                
            """.trimIndent()

            grapgQlTester.document(
                HELLO_WORLD_QUERY
            ).execute()
                .path("helloWorld")
                .entity(String::class.java)
                .isEqualTo("Hello World!")

        }

        it("should return Event When execute GetEvent query") {

            // language=GraphQl
            val GET_EVENT_QUERY = """
                query {
                    getEvent {
                        eventType
                        id
                    }
                }
            """.trimIndent()

            grapgQlTester.document(
                GET_EVENT_QUERY
            ).execute()
                .path("getEvent")
                .entity(Event::class.java)
                .satisfies { event ->
                    event.eventType.shouldBe("TESTING")
                    event.id.shouldNotBeNull()
                }
        }

        it("should return success data when execute greet Query") {

            // language=GraphQl
            val GREET_QUERY = """
                query greet(${'$'}name: String!){
                greet(name: ${'$'}name)
                }
            """.trimIndent()


            grapgQlTester.document(GREET_QUERY)
                .variable("name", "Robert")
                .execute()
                .path("greet")
                .entity(String::class.java)
                .satisfies{
                    it.shouldBe("Hello Robert")
                }
        }

        it("should throw error when greet Query executed with too short name") {

            // language=GraphQl
            val GREET_QUERY = """
                query greet(${'$'}name: String!){
                greet(name: ${'$'}name)
                }
            """.trimIndent()


            grapgQlTester.document(GREET_QUERY)
                .variable("name", "Rob")
                .execute()
                .errors()
                .satisfy{
                    error -> error.get(0).message.shouldBe("name should contain at least 5 characters")
                }
        }

    }


})
package com.graphql.first.resolver

import com.graphql.first.repositories.UserRepository
import com.graphql.first.services.UserService
import com.graphql.first.util.JwtUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeBetween
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.graphql.test.tester.HttpGraphQlTester
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserResolverTest(
    @LocalServerPort val randomServerPort: Int,
    @Value("\${secretKey}") val signedKey: String,
    val userService: UserService
) : DescribeSpec({

    lateinit var graphQlTesterForUnsecureOperations: HttpGraphQlTester
    lateinit var graphQlTesterForSecureOperations: HttpGraphQlTester

    beforeSpec {
        val client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:$randomServerPort/graphql")
            .build()

        graphQlTesterForUnsecureOperations = HttpGraphQlTester.create(client)

        graphQlTesterForSecureOperations = graphQlTesterForUnsecureOperations
            .mutate()
            .headers {
                it.set(
                    "Authorization", JwtUtil.generateJwtToken(
                        username = "John2",
                        signedSecret = signedKey,
                        roles = listOf("ROLE_USER")
                    )
                )
            }.build()
    }

    describe("User resolver") {

        it("should allow to create a new user") {

            // language=GraphQL
            val createUserMutation = """
                mutation {
                addUser(addUserInput: {name: "Vikas", password: "pass", roles: "ROLE_USER"})
                }
            """.trimIndent()

            val users = userService.getUsers(0, 10000)
            users.size.shouldBe(0)

            val userId = graphQlTesterForUnsecureOperations.document(
                createUserMutation
            ).execute()
                .path("addUser")
                .entity(String::class.java)
                .get()

            val newUserList = userService.getUsers(0, 10000)

            newUserList.size.shouldBe(1)

            newUserList.firstOrNull {
                it.id == UUID.fromString(userId)
            }.shouldNotBeNull()
                .name.shouldBe("Vikas")
        }

        it("should allow to users to login with valid credentials") {

            // language=GraphQL
            val createUserMutation = """
                mutation {
                addUser(addUserInput: {name: "Vikas", password: "pass", roles: "ROLE_USER"})
                }
            """.trimIndent()


            graphQlTesterForUnsecureOperations.document(
                createUserMutation
            ).executeAndVerify()

            // language=GraphQL
            val loginMutation = """
                mutation login(${'$'}username: String!, ${'$'} password: String!) {
                    login(username: ${'$'}username, password: ${'$'} password)
                }
            """.trimIndent()

            val token =  graphQlTesterForUnsecureOperations.document(loginMutation)
                .variable("username", "Vikas")
                .variable("password", "pass")
                .execute()
                .path("login")
                .entity(String::class.java)
                .get()

            token.shouldNotBeNull()
        }


    }


}) {
    @Autowired
    private lateinit var userRepository: UserRepository
}
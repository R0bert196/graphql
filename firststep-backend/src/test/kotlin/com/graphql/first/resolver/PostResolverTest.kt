package com.graphql.first.resolver

import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.services.UserService
import com.graphql.first.util.JwtUtil
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.graphql.test.tester.HttpGraphQlTester
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostResolverTest(
    @LocalServerPort val randomServerPort: Int,
    @Value("\${secretKey}") val signedKey: String,
    val userRepository: UserRepository,
    val postRepository: PostRepository
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

        val author = userRepository.save(
            UserEntity(
                name = "Robert",
                password = "pass",
                roles = "ROLE_USER",
            )
        )

        postRepository.save(
            PostEntity(
                title = "a brand new post for test",
                description = "this is a description",
                author = author
            )
        )
    }

    describe("Post resolver") {

        it("should allow user to fetch all posts") {

            //language=GraphQL
            val getPostsQuery = """
                query {
                    getPosts {
                        id
                        title
                        description
                        author {
                            name
                        }
                    }
                }
            """.trimIndent()

            data class UserTest(val name: String)
            data class PostTest(val id: UUID, val title: String, val description: String, val author: UserTest)

            val posts = graphQlTesterForSecureOperations.document(getPostsQuery)
                .execute().path("getPosts")
                .entityList(PostTest::class.java)
                .get()

            posts.shouldHaveSize(1)
            posts[0].title.shouldBe("a brand new post for test")
            posts[0].description.shouldBe("this is a description")
            posts[0].author.name.shouldBe("Robert")
        }

    }

})
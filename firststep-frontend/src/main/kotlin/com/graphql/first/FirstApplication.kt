package com.graphql.first

import com.graphql.first.entities.CommentEntity
import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.CommentRepository
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication
class FirstApplication {

    @Profile("!test")
    @Bean
    fun runner(
        userRepository: UserRepository,
        postRepository: PostRepository,
        commentRepository: CommentRepository,
        passwordEncoder: PasswordEncoder
    ): ApplicationRunner {
        return ApplicationRunner {

            val user = UserEntity(
                name = "Test user",
                password = passwordEncoder.encode("pass"),
                roles = "ROLE_USER"
            )

            val user2 = UserEntity(
                name = "Robert C",
                password = passwordEncoder.encode("robert"),
                roles = "ROLE_ADMIN"
            )

            userRepository.saveAll(listOf(user, user2))

            val postEntity = PostEntity(
                title = "Test title",
                description = "Test description",
                author = user
            )

            postRepository.saveAll(listOf(postEntity))

            val comment = CommentEntity(
                text = "comment for the user",
                author = user
            )

            val comment2 = CommentEntity(
                text = "comment for robert",
                author = user2
            )

            val comment3 = CommentEntity(
                text = "another comment for robert",
                author = user2
            )

            commentRepository.saveAll(listOf(comment, comment2, comment3))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FirstApplication>(*args)
}

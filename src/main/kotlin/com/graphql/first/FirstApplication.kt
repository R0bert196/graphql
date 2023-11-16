package com.graphql.first

import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootApplication
class FirstApplication {

    @Bean
    fun runner(
        userRepository: UserRepository,
        postRepository: PostRepository
    ): ApplicationRunner {
        return ApplicationRunner {

            val user = UserEntity(
                name = "Test user"
            )

            userRepository.save(user)

            val postEntity = PostEntity(
                title = "Test title",
                description = "Test description",
                author = user
            )

            postRepository.save(postEntity)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FirstApplication>(*args)
}

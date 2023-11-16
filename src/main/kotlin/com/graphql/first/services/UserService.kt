package com.graphql.first.services

import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.resolver.User
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    fun findByPostId(postId: UUID): User {
        val postEntity = postRepository.findById(postId).orElseThrow {
            RuntimeException("post does not exist for this user postId ${postId} ")
        }

        return User(
            id = postEntity.author.id,
            name = postEntity.author.name
        )
    }

}
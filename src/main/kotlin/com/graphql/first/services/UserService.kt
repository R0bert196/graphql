package com.graphql.first.services

import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.resolver.AddUserInput
import com.graphql.first.resolver.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*
import kotlin.RuntimeException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    fun findByPostId(postId: UUID): User {
        val postEntity = postRepository.findById(postId).orElseThrow {
            RuntimeException("post does not exist for this user postId $postId ")
        }

        return User(
            id = postEntity.author.id,
            name = postEntity.author.name
        )
    }

    fun addUser(addUserInput: AddUserInput): UUID {
        val userEntity = UserEntity(
            name = addUserInput.name
        )

        val user = userRepository.save(userEntity)

        user.id ?: throw RuntimeException("User id can't be null")

        return user.id
    }

    fun getUsers(page: Int, size: Int): List<User> {

        val page = PageRequest.of(page, size)

        return userRepository.findAll(page).map {
            User(
                id = it.id,
                name = it.name
            )
        }.toList()
    }

}
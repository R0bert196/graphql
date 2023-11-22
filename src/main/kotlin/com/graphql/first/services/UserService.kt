package com.graphql.first.services

import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.resolver.AddUserInput
import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.RuntimeException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findByPostId(postId: UUID): User {

        val userEntity = userRepository.findByPostsId(postId)
        return User(
            id = userEntity.id,
            name = userEntity.name,
        )
    }

    fun addUser(addUserInput: AddUserInput): UUID {
        val userEntity = UserEntity(
            name = addUserInput.name,
            password = passwordEncoder.encode(addUserInput.password),
            roles = addUserInput.roles
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
                name = it.name,
            )
        }.toList()
    }

    fun getAuthorById(id: UUID): User {

        val userEntity = userRepository.findById(id)
            .orElseThrow{ java.lang.RuntimeException("User id is not valid ${id}") }

        return User(
            id = userEntity.id,
            name = userEntity.name
        )
    }

    fun getUserByCommentId(commentId: UUID?): User? {

        commentId ?: throw RuntimeException("CommentId cannot be null")

        val userEntity = userRepository.findByCommentsId(commentId)

        if (userEntity != null) {
            return User(
                id = userEntity.id,
                name = userEntity.name
            )
        }

        return null
    }
}
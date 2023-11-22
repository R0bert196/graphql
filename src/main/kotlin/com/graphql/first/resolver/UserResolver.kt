package com.graphql.first.resolver

import com.graphql.first.services.UserService
import com.graphql.first.util.JwtUtil
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import java.util.*
import javax.crypto.SecretKey

@Controller
class UserResolver(
    private val userService: UserService,

    @Value("\${secretKey}")
    private val secretKey: String
) {

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @QueryMapping
    fun getUsers(@Argument page: Int, @Argument size: Int): List<User> {
        return userService.getUsers(page, size);
    }

    @MutationMapping
    fun addUser(@Argument addUserInput: AddUserInput) : UUID {
        return userService.addUser(addUserInput)
    }

    //  field resolver
    //  executed when a user is accessed through the Post node
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SchemaMapping(typeName = "Post")
    fun author(post: Post): User {
        val postId = post.id ?: throw RuntimeException("Post id cannot be null")

        return userService.findByPostId(postId)
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SchemaMapping(typeName = "Comment")
    fun author(comment: Comment): User? {
        return userService.getUserByCommentId(comment.id)
    }

    @MutationMapping
    fun login(@Argument username: String, @Argument password: String) =
        JwtUtil.generateJwtToken(username, secretKey, listOf("ROLE_ADMIN", "ROLE_USER"))

}

data class User(
    val id: UUID?,
    val name: String,
)

data class AddUserInput(
    val name: String,
    val password: String,
    val roles: String
)
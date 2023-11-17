package com.graphql.first.resolver

import com.graphql.first.services.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class UserResolver(private val userService: UserService) {

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
    @SchemaMapping(typeName = "Post")
    fun author(post: Post): User {
        val postId = post.id ?: throw RuntimeException("Post id cannot be null")

        return userService.findByPostId(postId)
    }

    @SchemaMapping(typeName = "Comment")
    fun author(comment: Comment): User {
        return userService.getUserByCommentId(comment.id)
    }
}

data class User(
    val id: UUID?,
    val name: String,
)

data class AddUserInput(
    val name: String
)
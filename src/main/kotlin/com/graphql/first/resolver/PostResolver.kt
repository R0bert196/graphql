package com.graphql.first.resolver

import com.graphql.first.services.PostService
import com.graphql.first.services.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.UUID


@Controller
class PostResolver(
    private val postService: PostService,
    private val userService: UserService
) {

    @QueryMapping
    fun getPosts(): List<Post> {
        return postService.getPosts()
    }

    @QueryMapping
    fun recentPosts(@Argument page: Int, @Argument size: Int): List<Post> {
        return postService.getPosts(page, size)
    }

    @MutationMapping
    fun addUser(@Argument addUserInput: AddUserInput) : UUID {
        return userService.addUser(addUserInput)
    }

    @SchemaMapping(typeName = "User")
    fun posts(user: User): List<Post> {
        val userId = user.id ?: throw RuntimeException("User id cannot be null")

        return postService.getPostsByAuthorId(userId);

    }

}

data class Post(
    val id: UUID?,
    val title: String,
    val description: String?
)


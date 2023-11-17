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
    fun addPost(@Argument addPostInput: AddPostInput): Post {
        return postService.addPost(addPostInput)
    }

    @SchemaMapping(typeName = "User")
    fun posts(user: User): List<Post> {
        val userId = user.id ?: throw RuntimeException("User id cannot be null")

        return postService.getPostsByAuthorId(userId);
    }

    @SchemaMapping(typeName = "User")
    fun totalPosts(user: User): Int {
        val userId = user.id ?: throw RuntimeException("User id cannot be null")

        return postService.getPostsByAuthorId(userId).size;
    }
}

data class Post(
    val id: UUID?,
    val title: String,
    val description: String?
)

data class AddPostInput (
    val title: String,
    val description: String,
    val authorId: UUID
)


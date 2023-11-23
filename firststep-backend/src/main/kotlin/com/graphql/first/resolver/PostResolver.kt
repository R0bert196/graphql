package com.graphql.first.resolver

import com.graphql.first.repositories.PostRepository
import com.graphql.first.services.PostService
import com.graphql.first.services.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import java.util.UUID


@Controller
class PostResolver(
    private val postService: PostService,
    private val userService: UserService, private val postRepository: PostRepository
) {

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @QueryMapping
    fun getPosts(): List<Post> {
        return postService.getPosts()
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @QueryMapping
    fun recentPosts(@Argument page: Int, @Argument size: Int): List<Post> {
        return postService.getPosts(page, size)
    }


    @PreAuthorize("hasRole('ADMIN')")
    @MutationMapping
    fun addPost(@Argument addPostInput: AddPostInput): Post {
        return postService.addPost(addPostInput)
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SchemaMapping(typeName = "User")
    fun posts(user: User): List<Post> {
        val userId = user.id ?: throw RuntimeException("User id cannot be null")

        return postService.getPostsByAuthorId(userId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SchemaMapping(typeName = "User")
    fun totalPosts(user: User): Int {
        val userId = user.id ?: throw RuntimeException("User id cannot be null")

        return postService.getPostsByAuthorId(userId).size;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SchemaMapping(typeName = "Comment")
    fun post(comment: Comment): Post? {
        return postService.getPostByCommentId(comment.id)
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


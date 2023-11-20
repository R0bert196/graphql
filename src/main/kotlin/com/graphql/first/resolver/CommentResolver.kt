package com.graphql.first.resolver

import com.graphql.first.services.CommentService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class CommentResolver(
    private val commentService: CommentService
) {


    @QueryMapping
    fun getComments(@Argument page: Int, @Argument size: Int): List<Comment> {
        return commentService.getComments(page, size)
    }

    @BatchMapping
    fun comments(posts: List<Post>): Map<Post, List<Comment>> {

        return commentService.getCommentsByPosts(posts)
    }

    @BatchMapping
    fun commentsForUser(users: List<User>): Map<User, List<Comment>> {
        return commentService.getCommentsForUsers(users)
    }

    // Non batching version is made with @SchemaMapping
//    @SchemaMapping(typeName = "Post")
//    fun comments(post: Post): List<Comment> {
//        return commentService.getCommentsByPostId(post.id)
//    }

//    @SchemaMapping(typeName = "User")
//    fun comments(user: User): List<Comment> {
//        return commentService.getCommentsByUserId(user.id)
//    }

    @MutationMapping
    fun addComment(@Argument("addCommentInput") addComment: AddCommentDTO): Comment {
        return commentService.saveComment(addComment)
    }


}


data class Comment(
    val id: UUID?,
    val text: String?,
)

data class AddCommentDTO(
    val text: String,
    val authorId: UUID?,
    val postId: UUID?
)
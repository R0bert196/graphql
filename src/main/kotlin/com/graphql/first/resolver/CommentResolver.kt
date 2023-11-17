package com.graphql.first.resolver

import com.graphql.first.services.CommentService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
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
}


data class Comment(
    val id: UUID?,
    val text: String?,
//    val author: User?,
//    val post: Post?,
)
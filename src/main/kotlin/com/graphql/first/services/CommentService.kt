package com.graphql.first.services

import com.graphql.first.repositories.CommentRepository
import com.graphql.first.resolver.Comment
import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.awt.print.Pageable
import java.util.*

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {

    fun getComments(page: Int, size: Int): List<Comment> {

        val pageRequest = PageRequest.of(page, size)

        return commentRepository.findAll(pageRequest).map {
            Comment(
                id = it.id,
                text = it.text
            )
        }.toList()
    }

    fun getCommentsByPostId(postId: UUID?): List<Comment> {

        postId ?: throw RuntimeException("Post id cannot be null")

        return commentRepository.findAllByPostId(postId).map {
            Comment(
                id = it.id,
                text = it.text
            )
        }.toList()
    }

    fun getCommentsByUserId(userId: UUID?): List<Comment> {

        userId ?: throw RuntimeException("User id cannot be null")

        return commentRepository.findAllByAuthorId(userId).map {
            Comment(
                id = it.id,
                text = it.text
            )
        }
    }
}
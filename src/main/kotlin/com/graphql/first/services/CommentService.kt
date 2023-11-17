package com.graphql.first.services

import com.graphql.first.entities.CommentEntity
import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.CommentRepository
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.resolver.AddCommentDTO
import com.graphql.first.resolver.Comment
import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.awt.print.Pageable
import java.util.*

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
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

    fun saveComment(addComment: AddCommentDTO): Comment {

        val userOptional: UserEntity? = addComment.authorId?.let { userRepository.findById(it).orElse(null) }
        val postOptional: PostEntity? = addComment.postId?.let { postRepository.findById(it).orElse(null) }

        val comment = CommentEntity(
            text = addComment.text,
            author = userOptional,
            post = postOptional
        )

        val savedComment = commentRepository.save(comment)

        return Comment(
            id = savedComment.id,
            text = savedComment.text,
        )
    }
}
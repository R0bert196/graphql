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
import org.springframework.security.core.context.SecurityContextHolder
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

        val loggedInUserName = SecurityContextHolder.getContext().authentication.name

        val userOptional = userRepository.findByName(loggedInUserName)

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

    fun getCommentsByPosts(posts: List<Post>): Map<Post, List<Comment>> {
        val comments =  commentRepository.findAllByPostIds(posts.mapNotNull { it.id }.toList())

        return posts.associateWith { post ->
                comments.filter { comment -> comment.post?.id == post.id }
                    .map { comment -> Comment(id = comment.id, text = comment.text) }
                    .toList()
        }
    }

    fun getCommentsForUsers(authors: List<User>): Map<User, List<Comment>> {
        val comments = commentRepository.findAllByAuthorIds(authors.mapNotNull { it.id }.toList())

        return authors.associateWith { author ->
            comments.filter { comment -> comment.author?.id == author.id }
                .map { comment -> Comment(id = comment.id, text = comment.text) }
                .toList()
        }
    }

}
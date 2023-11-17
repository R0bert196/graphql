package com.graphql.first.services

import com.graphql.first.repositories.CommentRepository
import com.graphql.first.resolver.Comment
import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.awt.print.Pageable

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
}
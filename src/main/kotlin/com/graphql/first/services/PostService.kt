package com.graphql.first.services

import com.graphql.first.repositories.PostRepository
import com.graphql.first.resolver.Post
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService(private val postRepository: PostRepository) {

    fun getPosts(): List<Post> {
        return postRepository.findAll().map {
            Post(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }

    fun getPostsByAuthorId(userId: UUID): List<Post> {
        return postRepository.findAllByAuthor_Id(userId).map {
            Post(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }
}
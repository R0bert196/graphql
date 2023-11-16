package com.graphql.first.services

import com.graphql.first.repositories.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository) {
}
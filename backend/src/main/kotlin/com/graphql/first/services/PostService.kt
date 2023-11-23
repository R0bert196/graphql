package com.graphql.first.services

import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import com.graphql.first.repositories.PostRepository
import com.graphql.first.repositories.UserRepository
import com.graphql.first.resolver.AddPostInput
import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*
import kotlin.RuntimeException

@Service
class PostService(private val postRepository: PostRepository, private val userRepository: UserRepository) {

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
        return postRepository.findAllByAuthorId(userId).map {
            Post(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }

    fun getPosts(page: Int, size: Int): List<Post> {
        val page =PageRequest.of(page, size)

        return postRepository.findAll(page).map {
            Post(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }.toList()
    }

    fun addPost(addPostInput: AddPostInput): Post {

    val author = userRepository.findById(addPostInput.authorId)
        .orElseThrow{ RuntimeException("User id is not valid ${addPostInput.authorId}")}

    val postEntity = PostEntity(
        title = addPostInput.title,
        description = addPostInput.description,
        author = author
    )

    val savedPost =  postRepository.save(postEntity)

    return Post(
        id = savedPost.id,
        title = savedPost.title,
        description = savedPost.description,
    )
    }

    fun getPostById(postId: UUID): Post {

        val postEntity = postRepository.findById(postId)
            .orElseThrow{ RuntimeException("Post id is not valid: $postId")}

        return Post(
            id = postEntity.id,
            title = postEntity.title,
            description = postEntity.description
        )
    }

    fun getPostByCommentId(commentId: UUID?): Post? {

        commentId ?: throw RuntimeException("CommentId cannot be null")

        val postEntity = postRepository.findByCommentsId(commentId)

        if (postEntity != null) {
            return Post(
                    id = postEntity.id,
                    title = postEntity.title,
                    description = postEntity.description
                )
            }
        return null
    }
}
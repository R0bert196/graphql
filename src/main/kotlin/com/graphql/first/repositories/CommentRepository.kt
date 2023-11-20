package com.graphql.first.repositories

import com.graphql.first.entities.CommentEntity
import com.graphql.first.resolver.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CommentRepository: JpaRepository<CommentEntity, UUID> {

    fun findAllByPostId(postId: UUID): List<CommentEntity>

    fun findAllByAuthorId(authorId: UUID): List<CommentEntity>

    @Query("select c from CommentEntity c where c.post.id in ?1")
    fun findAllByPostIds(postIds: List<UUID>): List<CommentEntity>

    @Query("select c from CommentEntity  c where c.author.id in ?1")
    fun findAllByAuthorIds(authorIds: List<UUID>): List<CommentEntity>
}
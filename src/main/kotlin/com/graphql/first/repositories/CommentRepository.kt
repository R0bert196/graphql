package com.graphql.first.repositories

import com.graphql.first.entities.CommentEntity
import com.graphql.first.resolver.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository: JpaRepository<CommentEntity, UUID> {
}
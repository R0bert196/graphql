package com.graphql.first.repositories

import com.graphql.first.entities.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PostRepository: JpaRepository<PostEntity, UUID> {
}
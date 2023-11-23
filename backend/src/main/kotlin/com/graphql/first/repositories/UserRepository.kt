package com.graphql.first.repositories

import com.graphql.first.entities.PostEntity
import com.graphql.first.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<UserEntity, UUID> {

    fun findByPostsId(postId: UUID): UserEntity

    fun findByCommentsId(commentId: UUID): UserEntity?

    fun findByName(username: String): UserEntity?
}
package com.graphql.first.entities

import com.graphql.first.resolver.Post
import com.graphql.first.resolver.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "comments")
class CommentEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    val text: String = "",

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: UserEntity? = null,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: PostEntity? = null,
)
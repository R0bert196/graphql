package com.graphql.first.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "posts")
data class PostEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null,

    @Column
    val title: String,

    @Column
    val description: String? = null,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: UserEntity,

    @OneToMany(mappedBy = "post")
    val comments: Set<CommentEntity> = setOf(),
)
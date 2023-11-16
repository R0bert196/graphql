package com.graphql.first.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    val author: UserEntity
)
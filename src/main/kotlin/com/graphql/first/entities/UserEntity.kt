package com.graphql.first.entities

import jakarta.persistence.*
import lombok.Data
import java.util.UUID


@Entity
@Table(name = "users")
@Data
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    val id: UUID? = null

    @Column
    val name: String = ""

    @OneToMany(mappedBy = "author")
    val posts: Set<PostEntity> = emptySet()
}
package com.graphql.first.resolver

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.UUID


@Controller
class PostResolver {

    @QueryMapping
    fun getPosts(): List<Post> {
        return listOf(
            Post(
                id = UUID.randomUUID(),
                title = "some title",
                description = "some description"
            ),
            Post(
                id = UUID.randomUUID(),
                title = "some other title",
                description = "some other description"
            )
        )
    }

    //  field resolver
    //  executed when a user is accessed through the Post node
    @SchemaMapping(typeName = "Post")
    fun author(post: Post): User {
        return User(
            id = UUID.randomUUID(),
            name = "title=${post.title} id-${post.id}"
        )
    }

    @SchemaMapping(typeName = "User")
    fun posts(user: User): List<Post> {
        return listOf(
            Post(
                id = UUID.randomUUID(),
                title = "test",
                description = "lalalalal"
            )
        )
    }
}

data class Post(
    val id: UUID,
    val title: String,
    val description: String
)

data class User(
    val id: UUID,
    val name: String
)
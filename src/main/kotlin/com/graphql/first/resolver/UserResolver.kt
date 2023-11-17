package com.graphql.first.resolver

import com.graphql.first.services.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserResolver(val userService: UserService) {

    @QueryMapping
    fun getUsers(@Argument page: Int, @Argument size: Int): List<User> {

        return userService.getUsers(page, size);
    }
}
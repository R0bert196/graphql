package com.graphql.first.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class AppSecurity {

    @Bean
    fun securityWebFilterChain(
        httpSecurity: HttpSecurity
    ): SecurityFilterChain {


        return httpSecurity
            .csrf{ it.disable() }
//            .addFilterAt()
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            .build()
    }
}
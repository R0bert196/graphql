package com.graphql.first.filter

import com.graphql.first.security.CustomAuthentication
import com.graphql.first.security.CustomAuthenticationManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val customAuthenticationManager: CustomAuthenticationManager
): OncePerRequestFilter() {



    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response)
            return
        }

        val authentication = CustomAuthentication(
            authenticated = false,
            token = request.getHeader("Authorization")
        )

        val authenticated = customAuthenticationManager.authenticate(authentication)

        if (authenticated.isAuthenticated) {
            val context = SecurityContextHolder.createEmptyContext()
            context.authentication = authenticated
            SecurityContextHolder.setContext(context)
            filterChain.doFilter(request, response)
        }
    }
}
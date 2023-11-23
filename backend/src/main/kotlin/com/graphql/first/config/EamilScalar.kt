package com.graphql.first.config

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import java.util.*
import java.util.regex.Pattern

class EmailScalar: Coercing<String, String> {

    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {

        if (dataFetcherResult is StringValue) {
            return dataFetcherResult.value.toString()
        }
        throw RuntimeException("Email is not valid")
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): String? {

        if (input is StringValue) {
            val possibleEmail = input.value.toString()
            if (isValidEmail(possibleEmail)) {
                return possibleEmail
            }
        }
        throw CoercingParseValueException("failed to parse email")
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String? {

        if (input is StringValue) {
            val possibleEmail = input.value.toString()
            if (isValidEmail(possibleEmail)) {
                return possibleEmail
            }
        }
        throw CoercingParseLiteralException("failed to parse email")
    }

    private fun isValidEmail(email: String) = Pattern.matches(
        "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$", email
    )
}
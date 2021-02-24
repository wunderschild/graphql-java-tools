package graphql.kickstart.tools

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionInput
import graphql.GraphQL

private val mapper = ObjectMapper()

fun assertNoGraphQlErrors(gql: GraphQL, args: Map<String, Any> = mapOf(), context: Any = Object(), closure: () -> String): Map<String, Any> {
    val result = gql.execute(ExecutionInput.newExecutionInput()
        .query(closure.invoke())
        .context(context)
        .root(context)
        .variables(args))

    if (result.errors.isNotEmpty()) {
        throw AssertionError("GraphQL result contained errors!\n${result.errors.map { mapper.writeValueAsString(it) }.joinToString { "\n" }}")
    }

    return result.getData() as Map<String, Any>
}


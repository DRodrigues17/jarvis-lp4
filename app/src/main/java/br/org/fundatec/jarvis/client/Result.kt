package br.org.fundatec.jarvis.client

sealed class Result<out S, out E> {
    data class Success<S>(val value: S) : Result<S, Nothing>()
    data class Error<E>(val value: E) : Result<Nothing, E>()

    fun get(): S? {
        return when (this) {
            is Success -> value
            else -> null
        }
    }
}
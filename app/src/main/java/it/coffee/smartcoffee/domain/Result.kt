package it.coffee.smartcoffee.domain

sealed class Result<out T : Any>

data class Success<out T : Any>(val value: T) : Result<T>()

sealed class Failure(val exception: Throwable) : Result<Nothing>()

class NetworkError(exception: Throwable) : Failure(exception)
class UnknownError(exception: Throwable) : Failure(exception)


package it.coffee.smartcoffee.domain

sealed class Result<out T>

data class Success<T>(val value: T) : Result<T>()

sealed class Failure(val exception: Throwable) : Result<Nothing>()

class NetworkError(exception: Throwable): Failure(exception)
class UnknownError(exception: Throwable): Failure(exception)


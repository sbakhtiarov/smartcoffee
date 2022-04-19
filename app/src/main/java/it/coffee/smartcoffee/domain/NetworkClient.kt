package it.coffee.smartcoffee.domain

interface NetworkClient {
    fun <T> createApi(clazz: Class<T>): T
}

inline fun <reified T> NetworkClient.create(): T {
    return createApi(T::class.java)
}
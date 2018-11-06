package com.frate.luxup.util

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(consumer: (T) -> Unit) {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            consumer(content)
        }
    }

    fun peekContent(): T = content
}
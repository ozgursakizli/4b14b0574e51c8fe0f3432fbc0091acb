package com.ozgursakizli.starshipdelivery.utilities

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? = if (hasBeenHandled) null else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T = content

}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}

sealed class SpaceshipEvents {
    object SaveSuccess : SpaceshipEvents()
    object SaveFailed : SpaceshipEvents()
}
package klaymore.patterns

import kotlinx.coroutines.channels.Channel

abstract class Worker<I, O> {

    lateinit var input: Channel<I> internal set
    lateinit var output: Channel<O> internal set

    abstract suspend fun go()
}
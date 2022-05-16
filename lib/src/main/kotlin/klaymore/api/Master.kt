package klaymore.api

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Master<I, O>(private val scaffold: Scaffold<I, O>) {

    var input: Channel<I> private set
    var output: Channel<O> private set

    init {
        input = Channel(Channel.UNLIMITED)
        output = Channel(Channel.UNLIMITED)
    }

    suspend fun go() {
        val worker = scaffold.build()

        worker.input = input
        worker.output = output
        output.onReceive

        worker.output.close() // Sends a close token immediately.

        worker.go()

    }

}
package klaymore.structs

import kotlinx.coroutines.channels.Channel
import java.util.concurrent.atomic.AtomicInteger

class Bridge<E>(fanIn: Int = 1) {

    private val fanInLeft = AtomicInteger(fanIn)

    private val channel = Channel<E>(Channel.UNLIMITED)

    suspend fun send(item: E) {
        channel.send(item)
    }

    suspend fun receive(): E {
        return channel.receive()
    }

    fun close() {
        if (fanInLeft.decrementAndGet() == 0) {
            channel.close()
        }
    }

    fun setFanIn(fanIn: Int) {
        fanInLeft.set(fanIn)
    }

    suspend fun forEach(action: (E) -> Unit) {
        for (item in channel) {
            action(item)
        }
    }
}
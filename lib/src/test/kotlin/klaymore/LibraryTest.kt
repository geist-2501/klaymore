package klaymore

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test
import kotlin.test.assertTrue

class LibraryTest {

    private var ids = AtomicInteger(0)

    private suspend fun sender(ch: Channel<String>) {
        delay(1000L)
        ch.send("Hello!")
        delay(1000L)
        ch.send("World!")
        ch.close()
    }

    private suspend fun receiver(ch: Channel<String>) {
        val id = ids.getAndIncrement()
        while (true) {
            try {
                val v = ch.receive()
                println("[$id] got $v")
            }
            catch (e: ClosedReceiveChannelException) {
                break
            }
        }

        println("$id finished")
    }

    @Test fun someLibraryMethodReturnsTrue() {
        runBlocking {
            val channel = Channel<String>(Channel.UNLIMITED)

            launch {
                sender(channel)
            }

            launch {
                receiver(channel)
            }
            launch {
                receiver(channel)
            }
        }
    }
}

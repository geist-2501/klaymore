package klaymore.patterns

import kotlinx.coroutines.channels.ClosedReceiveChannelException

class SingleWorker<I, O>(private val inner: (I) -> O): Worker<I, O>() {
    override suspend fun go() {
        while (true) {
            try {
                val item = input.receive()
                val result = inner(item)
                output.send(result)
            } catch (e: ClosedReceiveChannelException) {
                output.close()
                return
            }
        }
    }
}
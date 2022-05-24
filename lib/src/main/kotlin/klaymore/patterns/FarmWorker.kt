package klaymore.patterns

import klaymore.api.Scaffold
import kotlinx.coroutines.*

class FarmWorker<I, O>(private val scaffold: Scaffold<I, O>, private val numWorkers: Int): Worker<I, O>() {

    override suspend fun go() {

        output.setFanIn(numWorkers)

        coroutineScope {
            for (i in 0 until numWorkers) {
                val w = scaffold.build()
                w.input = input
                w.output = output
                launch(Dispatchers.Default) {
                    w.go()
                }
            }
        }
    }
}
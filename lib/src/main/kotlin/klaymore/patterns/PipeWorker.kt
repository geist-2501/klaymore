package klaymore.patterns

import klaymore.api.Scaffold
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PipeWorker<I, O>(
    private val stages: List<Scaffold<*, *>>
): Worker<I, O>() {

    private val workers = mutableListOf<Worker<*, *>>()

    override suspend fun go() {
        for (stage in stages) {
            val worker = stage.build()
            if (stage == stages.first()) {
                val front = worker as Worker<I, *>
                front.input = input
            }
            else {
                workers.last().connect(worker)
            }

            if (stage == stages.last()) {
                val back = worker as Worker<*, O>
                back.output = output
            }

            workers.add(worker)
        }

        coroutineScope {
            for (worker in workers) {
                launch {
                    worker.go()
                }
            }
        }
    }
}
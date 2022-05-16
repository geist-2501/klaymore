package klaymore.api

import klaymore.patterns.SingleWorker
import klaymore.patterns.Worker

class Single<I, O>(private val worker: (I) -> O): Scaffold<I, O>() {
    override fun build(): Worker<I, O> {
        return SingleWorker(worker)
    }
}
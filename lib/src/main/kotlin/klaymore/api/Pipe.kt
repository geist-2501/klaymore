package klaymore.api

import klaymore.patterns.PipeWorker
import klaymore.patterns.Worker

class Pipe<I, O>(
    initFunc: Pipe<I, O>.() -> Unit
): Scaffold<I, O>() {

    private val stages: MutableList<Scaffold<*, *>> = mutableListOf()

    init {
        this.initFunc()
    }

    override fun build(): Worker<I, O> {
        return PipeWorker(stages)
    }

    fun stage(s: Scaffold<*, *>) {
        stages.add(s)
    }
}
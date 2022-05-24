package klaymore.api

import klaymore.Bridge
import klaymore.patterns.Worker

class Master<I, O>(private val scaffold: Scaffold<I, O>): Worker<I, O>() {

    val tasks = mutableListOf<I>()
    val finishedTasks = mutableListOf<O>()

    init {
        input = Bridge()
        output = Bridge()
    }

    override suspend fun go() {
        // Load tasks.
        tasks.forEach { input.send(it) }

        val worker = scaffold.build()

        worker.input = input
        worker.output = output

        worker.input.close() // Sends a close token immediately.

        worker.go()

        output.forEach { finishedTasks.add(it) }
    }

}
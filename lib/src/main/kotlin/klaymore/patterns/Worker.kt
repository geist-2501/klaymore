package klaymore.patterns

import klaymore.structs.Bridge

abstract class Worker<I, O> {

    lateinit var input: Bridge<I> internal set
    lateinit var output: Bridge<O> internal set

    abstract suspend fun go()

    fun connect(other: Worker<*, *>) {
        val worker = other as Worker<O, *>
        output = Bridge()
        other.input = output
    }
}
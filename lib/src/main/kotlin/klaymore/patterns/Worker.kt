package klaymore.patterns

import klaymore.Bridge

abstract class Worker<I, O> {

    lateinit var input: Bridge<I> internal set
    lateinit var output: Bridge<O> internal set

    abstract suspend fun go()
}
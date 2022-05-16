package klaymore.api

import klaymore.patterns.Worker

abstract class Scaffold<I, O> {
    abstract fun build(): Worker<I, O>
}
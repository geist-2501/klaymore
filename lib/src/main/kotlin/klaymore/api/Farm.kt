package klaymore.api

import klaymore.patterns.FarmWorker
import klaymore.patterns.Worker

class Farm<I, O>(private val inner: Scaffold<I, O>, private val numFarms: Int): Scaffold<I, O>() {

    override fun build(): Worker<I, O> {
        return FarmWorker(inner, numFarms)
    }

}
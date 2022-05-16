package klaymore.patterns

class SingleWorker<I, O>(private val inner: (I) -> O): Worker<I, O>() {
    override suspend fun go() {
        for (item in input) {
            val res = inner(item)
            output.send(res)
        }

        input.close()
    }
}
package klaymore.patterns

import klaymore.api.Farm
import klaymore.api.Master
import klaymore.api.Single
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains


class FarmWorkerTest {

    @Test
    fun shouldPassSingleWorker() {
        runBlocking {
            // Arrange.
            val farm = Master<Int, Int>(Farm(Single { v -> v * 2 }, 1))
            farm.input.send(1)
            farm.input.send(2)
            farm.input.send(3)

            // Act.
            farm.go()

            // Assert.
            val res1 = farm.output.receive()
            val res2 = farm.output.receive()
            val res3 = farm.output.receive()
            val res = listOf(res1, res2, res3)

            assertContains(res, 1)
            assertContains(res, 4)
            assertContains(res, 9)
        }
    }
}
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
            val m = Master<Int, Int>(Farm(Single { v -> v * 2 }, 1))

            m.tasks.addAll(listOf(1, 2, 3))

            // Act.
            m.go()

            // Assert.
            val res = m.finishedTasks

            assertContains(res, 2)
            assertContains(res, 4)
            assertContains(res, 6)
        }
    }

    @Test
    fun shouldPassTripleWorker() {
        runBlocking {
            // Arrange.
            val m = Master<Int, Int>(Farm(Single { v -> v * 2 }, 3))

            m.tasks.addAll(listOf(1, 2, 3))

            // Act.
            m.go()

            // Assert.
            val res = m.finishedTasks

            assertContains(res, 2)
            assertContains(res, 4)
            assertContains(res, 6)
        }
    }


}
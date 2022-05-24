package klaymore.patterns

import klaymore.api.Master
import klaymore.api.Pipe
import klaymore.api.Single
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains

class PipeWorkerTest {


    @Test
    fun shouldPassSingleStage() {
        val m = Master<Int, Int>(Pipe {
            stage(Single<Int, Int> { task -> task * 2 })
        })

        m.tasks.addAll(listOf(1, 2, 3))

        // Act.
        runBlocking {
            m.go()
        }

        // Assert.
        val res = m.finishedTasks

        assertContains(res, 2)
        assertContains(res, 4)
        assertContains(res, 6)
    }

    @Test
    fun shouldPassThreeStage() {
        val m = Master<Int, Int>(Pipe {
            stage(Single<Int, Int> { task -> task * task })
            stage(Single<Int, Int> { task -> task - 1 })
            stage(Single<Int, Int> { task -> task * 2 })
        })

        m.tasks.addAll(listOf(1, 2, 3))

        // Act.
        runBlocking {
            m.go()
        }

        // Assert.
        val res = m.finishedTasks

        assertContains(res, 0)
        assertContains(res, 6)
        assertContains(res, 16)
    }

}
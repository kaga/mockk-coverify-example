package com.kaga.test_mockk

import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 *  implementation "org.jetbrains.kotlin:kotlin-stdlib:1.4.30"
 *  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
 *  testImplementation 'io.mockk:mockk:1.10.6'
 */
class TestMockkCoverify {
    /**
     * Verify with normal function works
     */
    @Test
    fun `should execute once`() {
        val spyClass = spyk(ExampleNormalClass())
        spyClass.execute()
        verify { spyClass.execute() }
    }

    /**
     * This works
     */
    @Test
    fun `should execute once with suspend class as well`() = runBlocking {
        val spyClass = spyk(ExampleSuspendClass())
        spyClass.execute()
        coVerify { spyClass.execute() }
    }

    /**
     * This test failed
     * java.lang.AssertionError: Verification failed: call 1 of 1: ExampleSuspendClass(#1).execute(any())). 2 matching calls found, but needs at least 1 and at most 1 calls
     * Calls:
     * 1) ExampleSuspendClass(#1).execute(continuation {})
     * 2) ExampleSuspendClass(#1).execute(continuation {})
     */
    @Test
    fun `should able to use coVerify to check number of executions`() = runBlocking {
        val spyClass = spyk(ExampleSuspendClass())
        spyClass.execute()
        coVerify(exactly = 1) { spyClass.execute() }
    }

    /**
     * coVerify exactly works with mockk, just not spyk
     */
    @Test
    fun `should able to check number of executions with mock`() = runBlocking {
        val mockClass = mockk<ExampleSuspendClass>(relaxed = true)
        mockClass.execute()
        coVerify(exactly = 1) { mockClass.execute() }
    }
}

class ExampleNormalClass {
    var numberOfExecution = 0
    fun execute() {
        runBlocking { delay(1000) }
        numberOfExecution++
    }
}

class ExampleSuspendClass {
    var numberOfExecution = 0
    suspend fun execute() {
        delay(1000)
        numberOfExecution++
    }
}

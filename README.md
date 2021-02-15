# Test Mockk CoVerify Example

Example project of testing coVerify feature in [mockk https://mockk.io/](https://mockk.io/).
Could possibility related to [this issue](https://github.com/mockk/mockk/issues/554)


```kotlin
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

class ExampleSuspendClass {
    var numberOfExecution = 0
    suspend fun execute() {
        delay(1000)
        numberOfExecution++
    }
}
```
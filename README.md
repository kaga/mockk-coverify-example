# Test Mockk CoVerify Example [![Lint](https://github.com/kaga/mockk-coverify-example/actions/workflows/lint.yml/badge.svg?branch=main)](https://github.com/kaga/mockk-coverify-example/actions/workflows/lint.yml) [![Mockk test case](https://github.com/kaga/mockk-coverify-example/actions/workflows/run-unit-test.yml/badge.svg?branch=main)](https://github.com/kaga/mockk-coverify-example/actions/workflows/run-unit-test.yml)


Example project of testing coVerify feature in [mockk https://mockk.io/](https://mockk.io/).
Seems to encountered an issue with coVerify expecting number of calls.
Could possibility related to [this issue](https://github.com/mockk/mockk/issues/554)

[Source file is located here TestMockkCoverify.kt](./app/src/test/java/com/kaga/test_mockk/TestMockkCoverify.kt)

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

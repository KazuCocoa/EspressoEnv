package com.example.kazuaki.espressoenv

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ExampleUnitTestWithJUnit5 {

    @Suppress("unused")
    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            println("@BeforeAll")
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("@AfterAll")
        }
    }

    @BeforeEach
    internal fun beforeEach() {
        println("@BeforeEach")
    }

    @AfterEach
    internal fun afterEach() {
        println("@AfterEach")
    }

    @Test // @Test in JUnit 4
    fun addition_isCorrect() {
        assertThat((2 + 2) as Int).isEqualTo(4)
    }

    @Suppress("unused")
    @Disabled // @Ignore in JUnit 4
    fun disabledTest() {
        // no
    }

    @Tag("Slow test") // @Category(Class) in JUnit 4
    @Test
    internal fun tag_test() {
        /// no
    }

    @ParameterizedTest // @Parameters
    @ValueSource(strings = arrayOf("1", "2", "3", "100", "200"))
    internal fun parameterizedTest(value: String) {
        assertThat(value).matches("[0-9]+")
    }

    @Nested
    @DisplayName("Nested test cases")
    internal inner class NestedTestClass {

        @Test
        @DisplayName("Test with Custom Display Name in nested class")
        internal fun inNestedClass() {
            assertThat("Nested" + "Class").isEqualTo("NestedClass")
        }
    }

    @TestFactory
    fun dynamicTestsGeneratedFromFactory(): Collection<DynamicTest> {
        val input = "Dynamic Test Input :)"

        return listOf(
                DynamicTest.dynamicTest("Length of input") { assertThat(input.length).isEqualTo(21) },
                DynamicTest.dynamicTest("Lowercase of input") { assertThat(input.toLowerCase()).isEqualTo("dynamic test input :)")},
                DynamicTest.dynamicTest("Uppercase of input") { assertThat(input.toUpperCase()).isEqualTo("DYNAMIC TEST INPUT :)")}
        )
    }
}

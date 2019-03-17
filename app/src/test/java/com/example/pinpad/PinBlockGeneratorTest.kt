package com.example.pinpad

import com.example.pinpad.model.PinBlockGenerator
import com.example.pinpad.model.RandomNumberGenerator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertNotNull
import org.mockito.Mockito.`when` as wen

class PinBlockGeneratorTest {

    @Mock
    private lateinit var randomNumberGenerator: RandomNumberGenerator

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        wen(randomNumberGenerator.randomInt(any(), any())).thenReturn(15)
    }

    @Test
    fun `generator creates return null when PIN is too short`() {
        val sut = PinBlockGenerator(randomNumberGenerator, EXAMPLE_PAN)

        val result = sut.generateFormat3Block("123")
        assertNull(result)
    }

    @Test
    fun `generator fills using random number generator`() {
        val sut = PinBlockGenerator(randomNumberGenerator, EXAMPLE_PAN)

        val result = sut.generateFormat3Block("1234")
        assertNotNull(result)

        verify(randomNumberGenerator, times(10)).randomInt(any(), any())
    }

    @Test
    fun `generator creates correct short PIN block`() {
        val sut = PinBlockGenerator(randomNumberGenerator, EXAMPLE_PAN)

        val result = sut.generateFormat3Block("1234")
        assertNotNull(result)
        assertEquals(16, result.length, "length should be 16 digits")
        assertEquals("3412AC89ABCDEF67", result)
    }

    @Test
    fun `generator creates correct odd length PIN block`() {
        val sut = PinBlockGenerator(randomNumberGenerator, EXAMPLE_PAN)

        val result = sut.generateFormat3Block("12345")
        assertNotNull(result)
        assertEquals(16, result.length, "length should be 16 digits")
        assertEquals("3512AC29ABCDEF67", result)
    }

    @Test
    fun `generator creates correct short PIN block with default PAN`() {
        val sut = PinBlockGenerator(randomNumberGenerator)

        val result = sut.generateFormat3Block("1234")
        assertNotNull(result)
        assertEquals(16, result.length, "length should be 16 digits")
        assertEquals("341216DDCCCCBBBB", result)
    }

    @Test
    fun `generator creates correct long PIN block`() {
        val sut = PinBlockGenerator(randomNumberGenerator, EXAMPLE_PAN)

        val result = sut.generateFormat3Block("123456")
        assertNotNull(result)
        assertEquals(16, result.length, "length should be 16 digits")
        assertEquals("3612AC20ABCDEF67", result)
    }

    companion object {
        const val EXAMPLE_PAN = "43219876543210987"
    }
}
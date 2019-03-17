package com.example.pinpad.model

import kotlin.random.Random

/**
 * Generates random numbers
 * @param seed value to use as a starting seed for generating numbers.
 */
class RandomNumberGenerator(seed: Long = System.currentTimeMillis()) {

    // This is not a secure way of getting random numbers.
    private val random = Random(seed)

    /**
     * Generate a random integer
     * @param from starting value (inclusive)
     * @param until ending value (exclusive)
     */
    fun randomInt(from: Int, until: Int) = random.nextInt(from, until)
}
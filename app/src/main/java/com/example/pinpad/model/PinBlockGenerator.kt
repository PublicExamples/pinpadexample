package com.example.pinpad.model

import com.example.pinpad.utility.toHexString
import com.example.pinpad.utility.toIntValue
import kotlin.random.Random

/**
 * Handles the creating PIN blocks for a given PAN (primary account number)
 * @param randomNumberGenerator used to create random padding if needed.
 * @param primaryAccountNumber PAN to use; defaults to '1111222233334444'
 */
class PinBlockGenerator(
    private val randomNumberGenerator: RandomNumberGenerator = RandomNumberGenerator(),
    private val primaryAccountNumber: String = "1111222233334444"
) {

    // This is not a secure random number generator...
    private val random = Random(System.currentTimeMillis())

    /**
     * For a given input PIN generate an ISO 3 (format 3) PIN block.
     * If the PIN is less than 4 characters long a null is returned.
     * @param pin the input PIN; should be longer than 4 characters
     */
    fun generateFormat3Block(pin: String): String? {

        return if (pin.length >= MINIMUM_FORMAT_3_LENGTH) {
            // The format is 3, then the length of the PIN then first two digits in the clear.
            val pinHeader = "3${pin.length.toHexString()}${pin[0]}${pin[1]}"

            var pinData = ""
            // do this 12 times. skip the first 4 PAN values then xor each with either the PIN value or a random.
            for (i in 2 until 14) {
                val pinCharacter = pin.getOrNull(i)?.toIntValue() ?: randomNumberGenerator.randomInt(10, 16)
                val panCharacter = primaryAccountNumber[i + 2].toIntValue()

                pinData += panCharacter.xor(pinCharacter).toHexString()
            }

            "$pinHeader$pinData"
        } else {
            null
        }
    }

    companion object {
        const val MINIMUM_FORMAT_3_LENGTH = 4
    }
}
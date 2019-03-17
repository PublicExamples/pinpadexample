package com.example.pinpad.utility

/**
 * Converts into a hex presentation 1->1 10->A
 */
fun Int.toHexString() = String.format("%01X", this)

/**
 * Get the integer value of a hexadecimal character e.g. 'a' becomes 10
 */
fun Char.toIntValue() = when (this) {
    in '0'..'9' -> toInt() - 48
    in 'a'..'f' -> toInt() - 87
    in 'A'..'F' -> toInt() - 55
    else -> 0
}
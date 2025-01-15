package spaghetti.remarkablerats.util

import java.util.stream.IntStream

fun intArrayToIntStream(intArray: IntArray): IntStream {
    val intStreamBuilder = IntStream.builder()
    for (value in intArray) {
        intStreamBuilder.add(value)
    }
    return intStreamBuilder.build()
}
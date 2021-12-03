package com.svmc.exampleapplication.basickotlin.functional

//can parse another function as parameter and/or return function
object HigherOrderFunctions {

//    parse function
    fun calculate (x: Int, y: Int, operation: (Int, Int)->Int): Int {
        return operation(x, y)
    }

    fun sum (x: Int, y: Int) = x + y

//    return function
    fun operation(): (Int, Int)->Int {
        return HigherOrderFunctions::sum
    }

    private fun spare (x: Int) = x* x

}

fun main() {
    val sum = HigherOrderFunctions.calculate(1,2, HigherOrderFunctions::sum)
    val mul = HigherOrderFunctions.calculate(1, 2) { a, b -> a * b }
}
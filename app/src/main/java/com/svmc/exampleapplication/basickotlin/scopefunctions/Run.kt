package com.svmc.exampleapplication.basickotlin.scopefunctions

//using for scoping and null checks
//execute a block code and return its result
//especial the object can accessed by this
class Run {
    fun getLength (str: String?) {
        println(" Value $str")
        val leng = str?.run {
//            can be accessed length empty()
            println(" is empty" + isEmpty())
            println(" Length $length")
            length
        }
        println(" length $leng")
    }

    fun main() {
        getLength(null)
        getLength("")
        getLength("Hello")
    }
}
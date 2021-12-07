package com.svmc.exampleapplication.basickotlin.scopefunctions

//using for scoping and null checks
//execute given block of code and returns the result of ít last expression

class Let {
    private val empty = "test".let {
        printString(it)
        "test".equals(it)
//        return the result of its last expression
        it.isEmpty()
        "test".equals("test")
    }

    fun printValue (value: Int) {
//        return true because get value of "test".equals("test")
        print(" Value: $empty")
    }
    private fun printString(str: String?) {
        print("String: $str")
    }
}
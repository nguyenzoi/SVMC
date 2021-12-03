package com.svmc.exampleapplication.basickotlin.specialclass

//a single implementation.
//this use singleton pattern

class Object {
    fun rentPrice (standardDays: Int, festivityDays: Int, specialDays: Int) {
//        object Expression, declare its members and access it within one function
        val dayRates = object {
            var standard: Int = 30 * standardDays
            var festivity: Int = 50 * festivityDays
            var special: Int = 100 * specialDays
        }

        val total = dayRates.standard + dayRates.festivity + dayRates.special

        print("Total time: $total")
    }

//    used inside class it's similar to the static methods
//    call directly method by ClassName
    companion object Display {
        fun printTime (nTimes: Int) {
            for (i in 1..nTimes) {
                print("$i ")
            }
        }
    }

}

//object Declaration
//used it to directly access its members
object DoAuth {
    fun takeParams (username: String, password: String) {
        print(" input Auth String $username $password")
    }
}


//fun main() {
//    Object.rentPrice(10, 2, 1)
//    DoAuth.takeParams("foo", "password")
//}





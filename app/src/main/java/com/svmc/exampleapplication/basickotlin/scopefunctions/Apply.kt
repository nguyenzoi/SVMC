package com.svmc.exampleapplication.basickotlin.scopefunctions

//Executes a block of code on an object and returns the object itself
//this functions is handy for initializing objects then chain other operations

data class Person (var name: String, var age: Int, var about: String) {
    constructor(): this("", 0, "")
}
class Apply {
    val nguyen = Person()
//    return object and then can chain other operations
    val stringDescription = nguyen.apply {
        name = "Nguyen"
        age = 10
        about = " Android developer "
    }.toString()
}
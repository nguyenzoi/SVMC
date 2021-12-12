package com.svmc.exampleapplication.basickotlin.functional

//simple way to create function
//be denoted very concisely in many cases thanks to type inference and it
//class LambdaFunction {
//    val uppercase1: (String)->String = {
//        str: String ->str.uppercase()
//    }
//    val uppercase2: (String)->String = {
//            str-> str.uppercase()
//    }
//
//    val uppercase3: (LambdaFunction, String, String) -> String = LambdaFunction::mUpper
//
//    fun mUpper(s1: String, s2: String): String {
//        return "$s1$s2".uppercase()
//    }
//
//    val uppercase4: (String) ->String = String::uppercase
//
//    fun show() {
//        uppercase1("Hello")
//        uppercase2("Hello")
//        uppercase4("Hello")
//    }
//}
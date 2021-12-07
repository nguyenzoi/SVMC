package com.svmc.exampleapplication.basickotlin.scopefunctions

class Configuration (var host: String, var port: Int)

//not extension function that can access members of its argument concisely
//can omit the instance name when referring to its members
//can execute the block with other when object null
class With {
    val configuration = Configuration(host = "107.113.185.143", port = 20)

    fun printConfig () {
        val value = with(configuration) {
            print("$host:$port")
        }
        configuration.print()
        val value1: String?
        value1 = null
        with(value1) {
            println("value1")
        }
    }

    fun Configuration.print() = "$host:$port"
}
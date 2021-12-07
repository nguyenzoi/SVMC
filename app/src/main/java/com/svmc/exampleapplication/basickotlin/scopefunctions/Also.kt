package com.svmc.exampleapplication.basickotlin.scopefunctions

//it executes a given block and returns the object called
//the object referent to it
//handy for embedding additional actions, such as logging in call chains

class Also {

    fun printCreationLog(p: Person) {
        print("name: ${p.name} age: ${p.age}")
    }

    fun init () {
        val nguyen = Person("Nguyen", 10, "Android Developer" )
            .also { printCreationLog(it) }
    }
}


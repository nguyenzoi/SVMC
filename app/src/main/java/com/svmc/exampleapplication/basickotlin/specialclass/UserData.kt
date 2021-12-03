package com.svmc.exampleapplication.basickotlin.specialclass

// used to store values
//provide with methods copy, toString, instances in collections


data class UserData(val name: String, val id: Int) {
    override fun equals(other: Any?): Boolean {
        return other is UserData && other.id == this.id
    }

    override fun toString(): String {
        return "name $name id = $id"
    }
}

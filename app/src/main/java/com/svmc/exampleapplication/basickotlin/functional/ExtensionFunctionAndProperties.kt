package com.svmc.exampleapplication.basickotlin.functional

//Can add new members to any class
class ExtensionFunctionAndProperties {

}

data class Item (val name: String, val price: Float)
//Collection it mean can parse arr, list, map...
data class Order (val items: Collection<Item>)

//add extension function
fun Order.maxPricedItemValue() = this.items.maxByOrNull { it.price }?.price?:0F
fun Order.maxPricedItemName() = this.items.maxByOrNull { it.price }?.name?:"No Products"

//add extension property
val Order.commaDelimitedItemNames: String
    get() = items.map {it.name}.joinToString()

fun main() {
    val order = Order(listOf(Item("bread", 11f), Item("wine", 12f)))
    print("Max price item value ${order.maxPricedItemValue()}")
    print("Max price item name ${order.maxPricedItemName()}")
    order.commaDelimitedItemNames
}
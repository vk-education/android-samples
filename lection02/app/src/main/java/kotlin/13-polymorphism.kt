package com.pa_vel.kotlin

open class PriceTag(protected val originPrice: Int) {
    open fun printPrice() {
        println("Price: $originPrice")
    }
}

class YellowPriceTag(originPrice: Int) : PriceTag(originPrice) {
    override fun printPrice() {
        println("DISCOUNT! Price: ${originPrice * 0.5}")
    }
}

fun main() {
    val priceTag: PriceTag = YellowPriceTag(100)
    val yellowPriceTag: YellowPriceTag = YellowPriceTag(100)
    priceTag.printPrice()
    yellowPriceTag.printPrice()


    priceTagPrinter(priceTag)
    priceTagPrinter(yellowPriceTag)
}

fun priceTagPrinter(priceTag: PriceTag) {
    priceTag.printPrice()
}
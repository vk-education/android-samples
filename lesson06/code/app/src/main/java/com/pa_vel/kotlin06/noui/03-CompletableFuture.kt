package com.pa_vel.kotlin06.noui

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


fun main() {
    println("Launch weight computation [${Thread.currentThread().name}]")
    val weightInKgFuture = CompletableFuture.supplyAsync {
        try {
            TimeUnit.SECONDS.sleep(1)
            println("Compute weight done [${Thread.currentThread().name}]")
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }
        65.0
    }

    println("Launch height computation [${Thread.currentThread().name}]")
    val heightInCmFuture = CompletableFuture.supplyAsync {
        try {
            TimeUnit.SECONDS.sleep(2)
            println("Compute height done [${Thread.currentThread().name}]")
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }
        177.8
    }

    val combinedFuture = weightInKgFuture
        .thenCombine(heightInCmFuture) { weightInKg: Double, heightInCm: Double ->
            println("Compute BMI [${Thread.currentThread().name}]")
            val heightInMeter = heightInCm / 100
            weightInKg / (heightInMeter * heightInMeter)
        }

    println("Your BMI is - ${combinedFuture.get()} [${Thread.currentThread().name}]")
}
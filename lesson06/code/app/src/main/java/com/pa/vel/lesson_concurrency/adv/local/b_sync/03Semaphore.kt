package com.pa.vel.lesson_concurrency.adv.local.b_sync

import java.util.concurrent.Semaphore
import kotlin.time.measureTime

fun main() {
    val parkingLot = ParkingLot(3)

    for (i in 1..10) {
        val carName = "Car $i"
        Thread { parkingLot.park(carName) }.start()
    }
}

class ParkingLot(spots: Int) {
    private val parkingSpots = Semaphore(spots)

    fun park(carName: String) {
        try {
            println("[${Thread.currentThread().name}] $carName is trying to park")
            val time = measureTime {
                parkingSpots.acquire()
            }
            println("$carName parked. Await time: $time")
            Thread.sleep((Math.random() * 3000).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            parkingSpots.release()
            println("$carName left the parking")
        }
    }
}


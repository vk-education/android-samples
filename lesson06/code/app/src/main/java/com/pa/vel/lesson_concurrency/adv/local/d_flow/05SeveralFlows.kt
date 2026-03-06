package com.pa.vel.lesson_concurrency.adv.local.d_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() {
    val battle = SeaBattle()
    runBlocking {

        battle.runBattle()

        coroutineScope {
//            combine(
//                battle.numbers,
//                battle.letters,
//            ) { number, letter ->
//                println("Next move: $letter$number")
//            }.collect()

//            battle.letters.zip(battle.numbers) { number, letter ->
//                println("Next move: $letter$number")
//            }.collect()
        }
    }
}

class SeaBattle {
    val numbers = MutableSharedFlow<Int>()
    val letters = MutableSharedFlow<Char>()

    val lettersChoose = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'G')

    suspend fun runBattle() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                numbers.emit(Random.nextInt(1, 9))
                delay(Random.nextLong(1000))
                letters.emit(lettersChoose.random())
                delay(Random.nextLong(1000))
            }
        }
    }
}

package com.pa_vel.kotlin06.noui

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


@SuppressLint("CheckResult")
fun main() {
    val observable = Observable.create { o ->
        println("Created on " + Thread.currentThread().name)
        o.onNext(1)
        o.onNext(2)
        o.onComplete()
    }
        .subscribeOn(Schedulers.io())

    println("Subscribe: " + Thread.currentThread().name)
    val disposable = observable
        .observeOn(Schedulers.newThread()) // Android: ui thread here
        .subscribe { i ->
            println(
                "Received " + i + " on " + Thread.currentThread().name
            )
        }


    Thread.sleep(1000)
    println("Finished main: " + Thread.currentThread().name)
}
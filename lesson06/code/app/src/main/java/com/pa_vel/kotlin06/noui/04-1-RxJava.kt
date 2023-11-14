package com.pa_vel.kotlin06.noui

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject


fun main() {
//    val subject = PublishSubject.create<Int>()
    val subject = ReplaySubject.createWithSize<Int>(1)
//    val subject = BehaviorSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(-1)
    val subscription = subject.subscribe(
        { println("Next is $it") },
        { println("Error $it") },
        { println("Completed") },
    )
//    subject.onNext(3)
//    subscription.dispose()
//    subject.onError(RuntimeException("test"))
//    subject.onNext(4)
//    subject.onNext(5)
//    subject.onNext(6)
    subject.onComplete()

}
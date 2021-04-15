package ru.hse.lection05.presentationlayer.fragments

interface INavigator {
    fun mainScreen()
    fun findPlaceScreen()
    fun pop()
}
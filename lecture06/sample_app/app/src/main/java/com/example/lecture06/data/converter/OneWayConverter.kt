package com.example.lecture06.data.converter

interface OneWayConverter<From, To> {

    fun convert(from: From): To
}
package com.pa_vel.kotlin

class User1 {
    val _name: String
    constructor(name: String) {
        _name = name
    }
}

class User2 constructor(name: String) {
    val _name: String = name
}

class User3(name: String) {
    val _name: String = name
}

class User4(name: String) {
    val _name: String = name
    constructor(nameSurname: Pair<String, String>) : this(nameSurname.first)
}

class User5 {
    val _name: String
    val _surname: String

    constructor(name: String, surname: String) {
        _name = name
        _surname = surname
    }
    constructor(name: String) : this(Pair(name, ""))
    constructor(nameSurname: Pair<String, String>)  {
        _name = nameSurname.first
        _surname = nameSurname.second
    }
}



fun main() {
    val user1 = User1("Jake")
    val user2 = User2("Jake")
    val user3 = User3("Jake")
    val user4 = User4("Jake" to "Warton")
    val user5 = User5("Jake" to "Warton")
    val user6= User5("Jake")

    println(user1._name)
    println(user2._name)
    println(user3._name)
    println(user4._name)
    println(user5._name)
}
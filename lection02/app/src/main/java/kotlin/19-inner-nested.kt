package com.pa_vel.kotlin

import java.util.logging.Logger
import kotlin.random.Random

class NetworkRequest(val params: Params) {
    fun execute(): RequestResult {
        println("Start request with $params")
        return if (Random.nextInt(5) == 0) {
            RequestResult.Error("error")
        } else {
            RequestResult.Ok("data")
        }
    }

    data class Params(val url: String = URL, val cookies: Map<String, String>)

    sealed interface RequestResult {
        data class Ok(val data: String) : RequestResult
        data class Error(val description: String) : RequestResult
    }

    companion object {
        private const val URL = "https://a.com"
    }
}

class UsersDao {
    fun fetchUsers(): DaoResult {
        return if (Random.nextInt(5) == 0) {
            DaoResult.DatabaseError("db error")
        } else {
            DaoResult.Received(listOf("Susan", "Kate"))
        }
    }

    sealed interface DaoResult {
        data class Received(val users: List<String>) : DaoResult
        data class DatabaseError(val description: String) : DaoResult
    }
}


fun main() {
    val networkRequest = NetworkRequest(NetworkRequest.Params(cookies = emptyMap()))
    val dao = UsersDao()

    val result1 = networkRequest.execute()
    val result2 = dao.fetchUsers()

    when (result1) {
        is NetworkRequest.RequestResult.Ok -> println(result1.data)
        is NetworkRequest.RequestResult.Error -> println(result1.description)
    }

    when (result2) {
        is UsersDao.DaoResult.Received -> println(result2.users)
        is UsersDao.DaoResult.DatabaseError -> println(result2.description)
    }

    val ownResult = NetworkRequest.RequestResult.Ok("data")
//    val fragmentLogger = Fragment.LifecycleLogger()

}

class Fragment {
    private val logger = Logger.getLogger("Fragment")
    val name = "Outer name"

    inner class LifecycleLogger {
        val name = "Inner name"
        fun onCreate() {
            name1
            logger.info("onCreate ${this@Fragment.name}")
        }

        fun onStart() {
            logger.info("onStart")
        }

        fun onStop() {
            logger.info("onStop")
        }

        fun onDestroy() {
            logger.info("onDestroy")
        }
    }

    companion object {
        private val name1 = "a"
    }
}
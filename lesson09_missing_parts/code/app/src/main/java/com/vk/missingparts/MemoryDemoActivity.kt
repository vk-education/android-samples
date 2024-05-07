package com.vk.missingparts

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import kotlin.random.Random

private val repositoryStatic = DataRepository()

class MemoryDemoActivity : AppCompatActivity(), DataListener {

//    private val repository = DataRepository()

    private val ref = WeakReference("aa")

    private lateinit var tv: TextView

//    private val someHeavyObject = Array(10000000) { 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)

        val str = ref.get()


        repositoryStatic.registerListener(this)
//        repositoryStatic.registerListener(Listener())

        findViewById<View>(R.id.update_value).setOnClickListener {
            repositoryStatic.notifyChanged(Random.nextInt().toString())
//            repository.notifyChanged(Random.nextInt().toString())
        }

        tv = findViewById(R.id.value)
//        tv.setOnClickListener {
//            val listener = { value: String ->
//                tv.text = value
//            }
//            repository.registerListener(listener)
//            repository.unregisterListener(listener)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onDataChanged(value: String?) {
        tv.text = value
    }

    private class Listener : DataListener {
        override fun onDataChanged(value: String?) {
//            tv.text = value
        }
    }
}

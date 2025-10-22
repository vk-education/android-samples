package com.lection.lesson_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    private val adapter: MainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            adapter.update(ArrayList<Item>(adapter.items).apply {
                this.add(Item(adapter.items.size + 1, "title${adapter.items.size + 1}"))
            })

        }

        recyclerView.adapter = adapter

        adapter.update(listOf(Item(1, "text1"), Item(2, "text2")))
    }
}
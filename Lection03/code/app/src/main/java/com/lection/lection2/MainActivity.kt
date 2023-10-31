package com.lection.lection2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var frame: CoordinatorLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frame = findViewById(R.id.frame)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        adapter = MainAdapter { nick ->
            Snackbar.make(frame, nick, Snackbar.LENGTH_LONG).show()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        savedInstanceState?.let {
            for (i in 0..it.getInt("size")) {
                adapter.items.add(People(name = "People 1", height = 160, nick = "button"))
            }
        }
        //adapter.items.add(People(name = "People 1", height = 160, nick = "people_1"))
        //adapter.items.add(People(name = "People 2", height = 166, nick = "people_2"))
        //adapter.items.add(People(name = "People 3", height = 190, nick = "people_3"))

        fab.setOnClickListener {
            adapter.items.add(People(name = "People 4", height = 170, nick = "button"))
            adapter.notifyItemInserted(adapter.items.size - 1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("size", adapter.itemCount)
    }
}
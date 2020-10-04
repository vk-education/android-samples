package ru.hse.lection02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        // Константы
        const val DATA_SIZE = 100
        const val STATE_CRITICAL = 0.8f

        protected val RANOMIZER = Random()
    }


    // Ссылка на RecyclerView
    lateinit var recycler: RecyclerView
    lateinit var droidAdapter: DroidAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем верстку
        setContentView(R.layout.activity_main)


        // Генерим данные
        val data = initializeData()


        // Класс для отслеживания клика по элементу
        val stateToggler = object: PersonViewHolder.IListener {
            override fun onDroidClicked(position: Int) {

                // Изменяем состояние дроида
                val droid = data[position]
                droid.state = (droid.state + 1) % 2

                // Уведомляем адаптер, что поменялся один из его элементов
                droidAdapter.notifyItemChanged(position)
            }
        }

        // Создаем адаптер
        droidAdapter = DroidAdapter(data, stateToggler)


        // Инициализируем RecyclerView
        recycler = findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = droidAdapter
        }
    }


    protected fun initializeData(): MutableList<Droid> {
        val data = mutableListOf<Droid>()

        // Наполняем лист в цикле
        for (position in 0 until DATA_SIZE) {

            // Генерим имя дроида
            val name = "Droid $position"

            // Получаем случайное число, и определяем состояние дроида
            val state = when (RANOMIZER.nextFloat() >= STATE_CRITICAL) {
                true -> Droid.STATE_REMOVED
                false -> Droid.STATE_NEW
            }

            // Создаем дроида и добавляем его в список
            val droid = Droid(name, state)
            data.add(droid)
        }

        return data
    }


    data class Droid(
            var name: String,
            var state: Int
    ) {
        companion object {
            const val STATE_REMOVED = 0
            const val STATE_NEW = 1
        }
    }

    class PersonViewHolder(itemView: View, val listener: IListener): RecyclerView.ViewHolder(itemView) {
        interface IListener {
            fun onDroidClicked(position: Int)
        }


        protected val name: TextView
        protected val image: ImageView


        init {
            // Находим View, которые будут отвечать за имя и картинку
            name = itemView.findViewById(R.id.name)
            image = itemView.findViewById(R.id.image)

            // Отслеживаем клик по элементу
            itemView.setOnClickListener {
                listener.onDroidClicked(adapterPosition)
            }
        }


        fun bind(item: Droid) {
            // Ставим имя дроида
            name.text = item.name

            // Ставим цвет, в зависимости от состояния дроида
            val colorResId = when (item.state) {
                Droid.STATE_REMOVED -> R.color.color_red
                Droid.STATE_NEW -> R.color.color_green
                else -> R.color.color_black
            }
            image.setImageResource(colorResId)
        }
    }

    class DroidAdapter(val data: List<Droid>, val listener: PersonViewHolder.IListener): RecyclerView.Adapter<PersonViewHolder>() {
        // Инициализируем ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {

            // Получаем инфлейтер и создаем нужный layout
            val inflater = LayoutInflater.from(parent.context)
            val layout = inflater.inflate(R.layout.item_droid, parent, false)

            // Создаем ViewHolder
            return PersonViewHolder(layout, listener)
        }

        // Вставляем данные во ViewHolder
        override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
            val item = data[position]

            holder.bind(item)
        }

        // Размер данных
        override fun getItemCount(): Int {
            return data.size
        }
    }
}

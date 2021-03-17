package com.mycompany.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HelloActivity : AppCompatActivity() {

    private val movies = generateMovieList().toMutableList()

    var adapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello_activity)
        val rvMovieList: RecyclerView = findViewById(R.id.hello_activity__rv_movie_list)
        adapter = MovieAdapter(movies)
        rvMovieList.adapter = adapter
        rvMovieList.layoutManager = LinearLayoutManager(this)
        val button: FloatingActionButton = findViewById(R.id.hello_activity__fab_add_movie)
        button.setOnClickListener { onClick() }
    }

    private fun onClick() {
        movies.add(
            Movie(
                "Мой новый фильм",
                "Здесь описание фильма",
                0
            )
        )
        adapter?.notifyDataSetChanged()
    }
}

private fun generateMovieList(): List<Movie> {
    return listOf(
        Movie(
            "Побег из Шоушенка",
            "Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни",
            R.drawable.movie_1
        ), Movie(
            "Матрица",
            "Жизнь Томаса Андерсона разделена на две части: днём он — самый обычный офисный работник, получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео, и нет места в сети, куда он не смог бы дотянуться",
            R.drawable.movie_2
        ), Movie(
            "Как приручить дракона",
            "Вы узнаете историю подростка Иккинга, которому не слишком близки традиции его героического племени, много лет ведущего войну с драконами",
            R.drawable.movie_3
        ), Movie(
            "12 стульев",
            "Во время революции и последовавшего за ней краткого периода военного коммунизма многие прятали свои ценности как можно надежнее",
            R.drawable.movie_4
        ), Movie(
            "Зеленая книга",
            "Утонченный светский лев, богатый и талантливый музыкант нанимает в качестве водителя и телохранителя человека, который менее всего подходит для этой работы",
            R.drawable.movie_5
        ), Movie(
            "Пираты Карибского моря: Проклятие Черной жемчужины",
            "Жизнь харизматичного авантюриста, капитана Джека Воробья, полная увлекательных приключений, резко меняется, когда его заклятый враг — капитан Барбосса — похищает корабль Джека, Черную Жемчужину, а затем нападает на Порт Ройал и крадет прекрасную дочь губернатора, Элизабет Свонн.",
            R.drawable.movie_6
        ), Movie(
            "Гарри Поттер и философский камень",
            "Жизнь десятилетнего Гарри Поттера нельзя назвать сладкой: его родители умерли, едва ему исполнился год, а от дяди и тётки, взявших сироту на воспитание, достаются лишь тычки да подзатыльники",
            R.drawable.movie_7
        )
    )
}
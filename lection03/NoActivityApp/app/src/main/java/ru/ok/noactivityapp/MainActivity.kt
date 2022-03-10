package ru.ok.noactivityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.ok.noactivityapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val movies = generateMovieList().toMutableList()
        val mainAdapter = MainAdapter(movies)
        binding.activityMainRv.adapter = mainAdapter
        binding.activityMainRv.layoutManager = LinearLayoutManager(this)

        binding.activityMainFab.setOnClickListener {
            movies.add(
                Movie(
                    "Новый фильм",
                    "Описания нет",
                    0,
                )
            )
            mainAdapter.notifyDataSetChanged()
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
}
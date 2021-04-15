package ru.hse.lection05.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.presentationlayer.models.WeatherViewModel
import ru.hse.lection05.objects.Place

class PlaceFragment: AbstractFragment() {
    val placeViewModel by viewModels<WeatherViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.content_place, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val place = arguments?.getSerializable(EXTRAS_PLACE) as Place
        placeViewModel.load(place)


        val progress = view.findViewById<View>(R.id.progress)
        val temp = view.findViewById<TextView>(R.id.temp)
        val weather = view.findViewById<TextView>(R.id.weather)

        val message = view.findViewById<TextView>(R.id.message).apply {
            setOnClickListener { placeViewModel.load(place) }
        }

        val title = view.findViewById<TextView>(R.id.title)
        title.text = place.name

        placeViewModel.weatherData().observe(viewLifecycleOwner) {
            progress.isVisible = it is Pending
            message.isVisible = it is Fail

            when (it) {
                is Success -> {
                    title.text = it.result.name
                    temp.text = it.result.main?.temp.toString()
                    weather.text = it.result.weather.firstOrNull()?.description
                }

                is Fail -> {
                    message.text = it.error?.message
                }
            }
        }

    }


    companion object {
        const val TAG = "PlaceFragment"

        const val EXTRAS_PLACE = "EXTRAS.PLACE"


        fun newInstance(place: Place): Fragment {
            val extras = Bundle().apply {
                putSerializable(EXTRAS_PLACE, place)
            }

            return PlaceFragment().apply {
                arguments = extras
            }
        }
    }
}
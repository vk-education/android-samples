package ru.hse.lection03.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.hse.lection03.R
import ru.hse.lection03.objects.Droid

class DroidDetailsFragment : DialogFragment() {
    companion object {
        const val EXTRAS_DROID = "DROID"


        // helper-метод для создания инстанса фрагмента
        // Это один из подходов в упрощении
        fun newInstance(droid: Droid): DroidDetailsFragment {
            // Создаем данные, которые потом положим в фрагмент
            val extras = Bundle().apply {
                putSerializable(EXTRAS_DROID, droid)
            }

            val fragment = DroidDetailsFragment().apply {
                arguments = extras
            }

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val droid = droid()
        if (droid != null) {
            // Устанавливаем имя
            view.findViewById<TextView>(R.id.name).text = droid.name

            val stateColor = when (droid.state) {
                Droid.STATE_REMOVED -> R.color.color_red
                Droid.STATE_NEW -> R.color.color_green
                else -> R.color.color_black
            }

            val stateTitle = when (droid.state) {
                Droid.STATE_REMOVED -> R.string.caption_droid_state_removed
                Droid.STATE_NEW -> R.string.caption_droid_state_new
                else -> R.string.caption_droid_state_unknown
            }

            view.findViewById<TextView>(R.id.state).apply {
                // Устанавливаем название состояния
                setText(stateTitle)

                // Красим подложку в цвет, ассоциированный с состоянием
                setBackgroundResource(stateColor)
            }
        }
    }

    // Метод для доставания объекта Droid из аргументов фрагмента
    fun droid(): Droid? {
        return arguments?.getSerializable(EXTRAS_DROID) as? Droid
    }
}
package ru.hse.lection03.presentationlayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection03.R
import ru.hse.lection03.businesslayer.DroidRepository
import ru.hse.lection03.objects.Droid
import ru.hse.lection03.presentationlayer.adapter.DroidAdapter
import ru.hse.lection03.presentationlayer.adapter.DroidViewHolder

class DroidListFragment : Fragment() {
    // Вариант кода, для общения с activity без Intent
    interface IListener {
        fun onDroidClicked(droid: Droid)
    }


    // Вариант кода, для общения с activity без Intent
    protected var listener: IListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = requireActivity() as? IListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // устанавливаем верстку
        return inflater.inflate(
                R.layout.content_list   // ресурс для "надувания" в дерево View
                , container             // родитель, куда потом будет вставлена верстка
                , false     // стоит false, что бы инфлейтер вернул верстку.
                                        // Если поставить true, то инфлейтер вставит верстку в parent, и вернет тоже parent
                                        // Мы сами в шоке от того, почему была сделана такая логика работы метод:(
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализируем View для отображения списка
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.apply {
            adapter = DroidAdapter(DroidRepository.instance.list(), DroidClickHandler())
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }


    // Одна из возможных реализаций отслеживания клика по элементу
    // обработчик клика по элементу
    inner class DroidClickHandler: DroidViewHolder.IListener {
        override fun onDroidClicked(position: Int) {
            val droid = DroidRepository.instance.item(position)

            // Вариант кода, для общения с activity без Intent
            listener?.onDroidClicked(droid)

            // Вариант кода, для android:launchMode="singleInstance"
//            val intent = MainActivity.droidDetailsIntent(requireContext(), droid)
//            startActivity(intent)
        }
    }
}
package company.vk.lection05.presentationlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import company.vk.lection05.R
import company.vk.lection05.objects.Strategies

class MenuFragment : Fragment() {
	interface IListener {
		fun onItemSelected(item: Strategies)
	}


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.content_menu, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val menu = view.findViewById<ViewGroup>(R.id.menu)
		Strategies.values().forEach { strategy ->
			// Это создание элемента для примера, в обычных ситуация такого кода стоит избегать
			val button = layoutInflater.inflate(R.layout.layout_button, menu, false) as Button
			button.text = strategy.name
			button.setOnClickListener { selectItem(strategy) }
			menu.addView(button)
		}
	}

	private fun selectItem(strategy: Strategies) {
		val navigator = activity as IListener
		navigator.onItemSelected(strategy)
	}


	companion object {
		const val TAG = "MenuFragment"
	}
}
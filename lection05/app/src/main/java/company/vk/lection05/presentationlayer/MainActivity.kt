package company.vk.lection05.presentationlayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import company.vk.lection05.R
import company.vk.lection05.objects.Strategies

class MainActivity : AppCompatActivity(), MenuFragment.IListener {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) {
			initializeMenu()
		}
	}

	override fun onItemSelected(item: Strategies) {
		initializeList(item)
	}


	protected fun initializeMenu() {
		val menuFragment = MenuFragment()

		supportFragmentManager.beginTransaction()
			.replace(R.id.container, menuFragment, MenuFragment.TAG)
			.commit()
	}

	protected fun initializeList(item: Strategies) {
		val listFragment = SimpleListFragment.newInstance(item)

		supportFragmentManager.beginTransaction()
			.replace(R.id.container, listFragment, item.name)
			.addToBackStack(item.name)
			.commit()
	}
}
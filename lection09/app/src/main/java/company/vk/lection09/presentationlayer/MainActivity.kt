package company.vk.lection09.presentationlayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import company.vk.base.loggers.Logger
import company.vk.lection09.R

class MainActivity : AppCompatActivity(), MenuFragment.IListener {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.host, MenuFragment.newInstance(), MenuFragment.TAG)
				.commit()
		}
	}

	override fun showWebView() {
		replaceFragment(WebViewFragment.newInstance(), WebViewFragment.TAG)
	}

	override fun showWebViewInteraction() {
		replaceFragment(WebViewInteractionFragment.newInstance(), WebViewInteractionFragment.TAG)
	}

	override fun filmList() {
		replaceFragment(FilmListFragment.newInstance(), FilmListFragment.TAG)
	}


	protected fun replaceFragment(fragment: Fragment, tag: String) {
		Logger.d("replaceFragment($fragment, $tag)", "TECH")

		supportFragmentManager.beginTransaction()
			.replace(R.id.host, fragment, tag)
			.addToBackStack(null)
			.commitAllowingStateLoss()
	}
}
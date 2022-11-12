package company.vk.myapplication

import android.annotation.SuppressLint
import android.content.Context
import company.vk.myapplication.businesslayer.CatProvider
import company.vk.myapplication.datalayer.IAccessor

@SuppressLint("StaticFieldLeak")
object ServiceLocator {
	lateinit var context: Context

	private val accessor by lazy { IAccessor.create(context.getString(R.string.base_url))  }

	fun initialize(context: Context) {
		this.context = context.applicationContext
	}

	fun provider(): CatProvider {
		return CatProvider(accessor)
	}
}
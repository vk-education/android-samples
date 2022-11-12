package company.vk.lection05.businesslayer

import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.objects.Item
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Thread.sleep

internal class RxAppendingItemProvider(accessor: IItemAccessor): AbstractItemProvider(accessor) {
	override fun load(callback: (List<Item>) -> Unit) {
		val observable = Observable.create {
			val result = accessor.items()
			val response = mutableListOf<Item>()

			result.forEach { item ->
				sleep(500)
				response.add(item)
				it.onNext(response.toList())
			}

			it.onComplete()
		}.subscribeOn(Schedulers.newThread())

		observable.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
			{callback(it)},
			{it.printStackTrace()}
		)
	}
}
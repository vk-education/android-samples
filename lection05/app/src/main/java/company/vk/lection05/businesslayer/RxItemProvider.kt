package company.vk.lection05.businesslayer

import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.datalayer.SimpleItemAccessor
import company.vk.lection05.objects.Item
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

internal class RxItemProvider(accessor: IItemAccessor): AbstractItemProvider(accessor) {
	override fun load(callback: (List<Item>) -> Unit) {
		Observable.fromSingle<List<Item>> { accessor.items() }
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ callback(it) },
				{ it.printStackTrace() }
			)
	}
}
package company.vk.lection05.businesslayer

import android.os.Handler
import android.os.Looper
import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.datalayer.SimpleItemAccessor
import company.vk.lection05.objects.Item
import kotlin.concurrent.thread

@Deprecated("Не используйте эту механику. Она потребует дополнительных действий")
internal class ThreadItemProvider(accessor: IItemAccessor): AbstractItemProvider(accessor) {
	override fun load(callback: (List<Item>) -> Unit) {
		thread {
			try {
				val result = accessor.items()
				MAIN_HANDLER.post { callback(result) }
			} catch (error: Throwable) {
				error.printStackTrace()
			}
		}
	}


	companion object {
		protected val MAIN_HANDLER = Handler(Looper.getMainLooper())
	}
}
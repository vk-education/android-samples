package com.vk.missingparts

class DataRepository {
    private val listeners = mutableSetOf<DataListener>()
//    private val listeners = mutableSetOf<(String) -> Unit>()

    fun registerListener(listener: DataListener) {
        listeners.add(listener)
    }
    fun unregisterListener(listener: DataListener) {
        listeners.remove(listener)
    }

    fun notifyChanged(newValue: String) {
        android.util.Log.d("taag", "Listeners size: ${listeners.size}")
        listeners.forEach {
             it.onDataChanged(newValue)
//            it.invoke(newValue)
        }
    }
}

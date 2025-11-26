package company.vk.lesson07.datalayer

import android.content.Context
import android.os.Environment
import company.vk.lesson07.objects.Plate

class ExternalDataFileAccessor(context: Context): AbstractFileAccessor(Environment.getExternalStorageDirectory(), "ExternalDataFileAccessor") {
    override fun plates(): List<Plate> {
        if (isExternalStorageReadable()) {
            return super.plates()
        }

        throw IllegalStateException("External storage is not mounted")
    }

    override fun createPlate(color: Int): Plate {
        if (isExternalStorageWritable()) {
            return super.createPlate(color)
        }

        throw IllegalStateException("External storage is not mounted")
    }

    override fun clearPlates(): Boolean {
        if (isExternalStorageWritable()) {
            return super.clearPlates()
        }

        throw IllegalStateException("External storage is not mounted")
    }

    protected fun isExternalStorageWritable(): Boolean  {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    protected fun isExternalStorageReadable(): Boolean  {
        Environment.getExternalStorageDirectory()
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }
}

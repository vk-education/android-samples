package company.vk.lesson06

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStream

object AssetsUtils {
    fun fromAssets(assetFilePath: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        val stream = context.assets.open(assetFilePath)
        val result = load(stream)

        return result
    }

    fun load(stream: InputStream): String {
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        return String(buffer, Charsets.UTF_8)
    }
}
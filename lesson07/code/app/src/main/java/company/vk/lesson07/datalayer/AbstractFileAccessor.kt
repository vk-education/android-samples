package company.vk.lesson07.datalayer

import android.graphics.Color
import company.vk.lesson07.objects.Plate
import java.io.File

abstract class AbstractFileAccessor(dir: File, name: String): IPlateAccessor {
    protected val file = File(dir, name)

    override fun plates(): List<Plate> {
        if (!file.exists()) {
            return emptyList()
        }

        val list = mutableListOf<Plate>()

        file.readLines().forEachIndexed { index, string ->
            val plate = Plate(
                (index + 1).toString(),
                string.toIntOrNull()?: Color.BLACK
            )

            list.add(plate)
        }

        return list
    }

    override fun createPlate(color: Int): Plate {
        if (!file.exists()) {
            file.createNewFile()
        }

        val index = file.readLines().size + 1
        val plate = Plate(index.toString(), color)

        if (index > 1) {
            file.appendText("\n")
        }

        file.appendText(plate.color.toString())

        return plate
    }

    override fun clearPlates(): Boolean {
        if (file.exists()) {
            return file.delete()
        }

        return true
    }
}
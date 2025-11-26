package company.vk.lesson07.datalayer

import android.annotation.SuppressLint
import android.content.Context
import company.vk.lesson07.datalayer.sql.PlateSqlHelper
import company.vk.lesson07.objects.Plate

class SqlAccessor(context: Context) : IPlateAccessor {
    protected val helper = PlateSqlHelper(context)

    override fun createPlate(color: Int): Plate {
        val value = helper.plateCount() + 1
        val plate = Plate(value.toString(), color)

        helper.insertPlate(plate.value, plate.color)

        return plate
    }

    @SuppressLint("Range")
    override fun plates(): List<Plate> {
        return helper.plates()
    }

    override fun clearPlates(): Boolean {
        return helper.clear() >= 0
    }
}
package company.vk.lesson07.businesslayer.providers

import company.vk.lesson07.datalayer.IPlateAccessor
import company.vk.lesson07.objects.Plate

class PlateProvider(val accessor: IPlateAccessor) {
    suspend fun all(): List<Plate> {
        return accessor.plates()
    }

    suspend fun create(color: Int): Plate {
        return accessor.createPlate(color)
    }

    suspend fun clear(): Boolean {
        return accessor.clearPlates()
    }
}

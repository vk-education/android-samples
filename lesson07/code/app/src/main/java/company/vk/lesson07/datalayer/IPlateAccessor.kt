package company.vk.lesson07.datalayer

import company.vk.lesson07.objects.Plate

interface IPlateAccessor {
    fun plates(): List<Plate>
    fun createPlate(color: Int): Plate
    fun clearPlates(): Boolean
}
package company.vk.lesson07.datalayer

import company.vk.lesson07.objects.Plate

class MemoryAccessor : IPlateAccessor {
    protected val memory = mutableListOf<Plate>()

    override fun createPlate(color: Int): Plate {
        val value = memory.size + 1
        val plate = Plate(value.toString(), color)

        memory.add(plate)

        return plate
    }

    override fun plates(): List<Plate> {
        return memory.toList()
    }

    override fun clearPlates(): Boolean {
        memory.clear()

        return true
    }
}
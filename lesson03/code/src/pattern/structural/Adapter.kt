package pattern.structural

fun main() {
    val cardReader: USB = CardReader(MemoryCard())
    cardReader.connectWithUsbCable()
}

interface USB {
    fun connectWithUsbCable()
}

class MemoryCard {
    fun insert() {
        println("Карта памяти успешно вставлена!")
    }

    fun copyData() {
        println("Данные скопированы на компьютер!")
    }
}

class CardReader(private val memoryCard: MemoryCard) : USB {
    override fun connectWithUsbCable() {
        memoryCard.insert()
        memoryCard.copyData()
    }
}
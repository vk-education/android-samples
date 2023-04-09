package company.vk.Lesson06.objects

class Item {
	var id = 0L
	var name = ""
	var image = ""
	var species = ""

	override fun hashCode(): Int {
		return id.hashCode()
	}

	override fun equals(other: Any?): Boolean {
		return other is Item &&
				id == other.id &&
				name == other.name &&
				image == other.image &&
				species == other.species
	}
}
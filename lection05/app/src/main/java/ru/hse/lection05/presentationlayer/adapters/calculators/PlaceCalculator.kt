package ru.hse.lection05.presentationlayer.adapters.calculators

import ru.hse.lection05.objects.Place

class PlaceCalculator: SimpleCalculator<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }
}
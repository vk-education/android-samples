package ru.hse.lection05.presentationlayer.adapters

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.hse.lection05.presentationlayer.fragments.PlaceFragment
import ru.hse.lection05.objects.Place

class PlacesAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    protected var pages = listOf<Place>()


    override fun getItemCount() = pages.size

    override fun createFragment(position: Int): Fragment {
        val place = pages[position]
        return PlaceFragment.newInstance(place)
    }


    fun submitList(list: List<Place>) {
        val result = Calculator(pages, list).execute()
        pages = list

        result.dispatchUpdatesTo(this)
    }


    protected class Calculator(val oldList: List<Place>, val newList: List<Place>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }


        fun execute() = DiffUtil.calculateDiff(this, true)
    }
}
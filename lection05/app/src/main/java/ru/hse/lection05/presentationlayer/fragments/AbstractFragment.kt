package ru.hse.lection05.presentationlayer.fragments

import androidx.fragment.app.Fragment

abstract class AbstractFragment: Fragment() {
    fun navigator(): INavigator {
        var nvaigator = parentFragment as? INavigator

        if (nvaigator == null) {
            nvaigator = requireActivity() as? INavigator
        }

        return nvaigator!!
    }
}
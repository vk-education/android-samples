package com.example.lecture5

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentLifecycleLogger : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentAttached")
    }


    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentViewCreated")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentStarted")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentResumed")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentPaused")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentSaveInstanceState")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentViewDestroyed")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentDestroyed")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        Log.d(TAG, "${f::class.simpleName}(#${f.tag}) onFragmentDetached")
    }

    companion object {
        private const val TAG = "FR_TAH"
    }
}
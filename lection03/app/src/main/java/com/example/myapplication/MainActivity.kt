package com.example.myapplication

import android.os.Bundle

class MainActivity : AbstractActivity(), INavigation  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_panel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


    }

    override fun showData(data: String) {
        val fragment = DataFragment.create(data)

        val dataFragment = supportFragmentManager.findFragmentByTag("DATA") as? DataFragment

        when (dataFragment) {
            null -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment, "DATA")
                        .addToBackStack(null)
                        .commit()
            }

            else -> {
                dataFragment.updateData(data)
            }
        }
    }
}
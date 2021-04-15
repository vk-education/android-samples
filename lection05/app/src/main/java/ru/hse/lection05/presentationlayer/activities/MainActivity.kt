package ru.hse.lection05.presentationlayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.fragments.INavigator
import ru.hse.lection05.presentationlayer.fragments.PlaceAddFragment
import ru.hse.lection05.presentationlayer.fragments.PlaceListFragment
import ru.hse.lection05.presentationlayer.fragments.SplashFragment

class MainActivity : AppCompatActivity(), INavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SplashFragment(), "SPLASH")
                .commit()
        }
    }

    override fun mainScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaceListFragment(), "PLACES")
            .commit()
    }

    override fun findPlaceScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaceAddFragment(), "ADD")
            .addToBackStack(null)
            .commit()
    }

    override fun pop() {
        supportFragmentManager.popBackStack()
    }
}
package com.example.lecture5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleLogger(), true)

        val navController =
            findViewById<FragmentContainerView>(R.id.nav_host_fragment_activity_main)
                .getFragment<NavHostFragment>().navController

        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
    }
}
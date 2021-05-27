package ru.hse.lection10

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.hse.lection10.databinding.ActivityMainBinding


typealias BarChangeListener = (offsetTop: Int, offsetBottom: Int) -> Unit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val isMainTab = MAIN_TABS.contains(destination.id)
            binding.navView.isVisible = isMainTab

            binding.appbar.isVisible = isMainTab
//            when (isMainTab) {
//                true -> supportActionBar?.show()
//                false -> supportActionBar?.hide()
//            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(MAIN_TABS)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        val actionBarTopPadding = binding.appbar.paddingTop
        val bottomBarBottomPadding = binding.navView.paddingBottom
        setBarTransparency { offsetTop, offsetBottom ->
            binding.appbar.updatePadding(top = actionBarTopPadding + offsetTop)
            binding.navView.updatePadding(bottom = bottomBarBottomPadding + offsetBottom)
        }
    }

    fun setBarTransparency(listener: BarChangeListener) {
        setInsetHandler(window.decorView, listener)
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
    }

    fun calculateDesiredBottomInset(topInset: Int, bottomInset: Int, listener: BarChangeListener): Int {
        val hasKeyboard = isKeyboardAppeared(bottomInset)
        val desiredBottomInset = if (hasKeyboard) bottomInset else 0
        listener(topInset, if (hasKeyboard) 0 else bottomInset)
        return desiredBottomInset
    }

    fun isKeyboardAppeared(bottomInset: Int) = bottomInset / resources.displayMetrics.heightPixels.toDouble() > .25

    fun setInsetHandler(view: View, listener: BarChangeListener) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val desiredBottomInset = calculateDesiredBottomInset(
                insets.systemWindowInsetTop,
                insets.systemWindowInsetBottom,
                listener
            )

            val tmp = Insets.of(0, 0, 0, desiredBottomInset)
            val compatInsets = WindowInsetsCompat.Builder()
                .setSystemWindowInsets(tmp)
                .build()

            ViewCompat.onApplyWindowInsets(view, compatInsets)
        }
    }


    companion object {
        val MAIN_TABS = setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
    }
}
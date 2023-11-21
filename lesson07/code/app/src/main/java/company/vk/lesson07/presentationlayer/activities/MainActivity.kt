package company.vk.lesson07.presentationlayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import company.vk.lesson07.R
import company.vk.lesson07.businesslayer.ProviderFactory
import company.vk.lesson07.presentationlayer.fragments.MenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Этот код сделан для примера! Не следует так делать в релизных приложениях!
        ProviderFactory.initialize(applicationContext)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.host, MenuFragment(), MenuFragment.TAG)
            .commitAllowingStateLoss()
    }
}
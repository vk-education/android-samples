package company.vk.education.lection07.presentationlayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import company.vk.education.lection07.R
import company.vk.education.lection07.presentationlayer.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment(), "TAG")
                .commit()
        }
    }
}
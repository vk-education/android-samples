package ru.mail.education.lesson05

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class FragmentedActivity : AppCompatActivity(), CustomFragment.IListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fragmented)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CustomFragment())
                .commitAllowingStateLoss()
        }
    }


    override fun openFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CustomFragment.newInstance(), "TAG")
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .addToBackStack(null)
            .commit()
    }


    companion object {
        const val TAG = "FragmentedActivity"
    }
}
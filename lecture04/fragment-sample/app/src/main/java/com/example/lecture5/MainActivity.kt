package com.example.lecture5

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleLogger(), true)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.container, SimpleFragment.newInstance(), count.toString())
            }
        } else {
            count = savedInstanceState.getInt(COUNT_KEY, 0)
        }

        val checkBox = findViewById<CheckBox>(R.id.backstack_checkbox)
        findViewById<Button>(R.id.replace_button).setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.container, SimpleFragment.newInstance(), (++count).toString())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.remove_button).setOnClickListener {
            supportFragmentManager.commit {
                remove(supportFragmentManager.fragments.last())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.add_button).setOnClickListener {
            supportFragmentManager.commit {
                add(R.id.container, SimpleFragment.newInstance(), (++count).toString())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.detach_button).setOnClickListener {
            supportFragmentManager.commit {
                detach(supportFragmentManager.fragments.last())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.hide_button).setOnClickListener {
            supportFragmentManager.commit {
                hide(supportFragmentManager.fragments.last())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.show_button).setOnClickListener {
            supportFragmentManager.commit {
                show(supportFragmentManager.fragments.last())
                addToBackStack(checkBox.isChecked)
            }
        }
        findViewById<Button>(R.id.pop_button).setOnClickListener {
            supportFragmentManager.popBackStack()
        }
        findViewById<Button>(R.id.pop_inclusive_button).setOnClickListener {
            supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
        }

    }

    private fun FragmentTransaction.addToBackStack(isChecked: Boolean) =
        if (isChecked) addToBackStack(null) else this


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(COUNT_KEY, count)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    companion object {
        private const val COUNT_KEY = "count"
    }
}
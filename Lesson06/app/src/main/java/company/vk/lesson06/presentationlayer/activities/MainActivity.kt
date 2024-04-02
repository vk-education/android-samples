package company.vk.lesson06.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import company.vk.lesson06.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.fields).setOnClickListener(this)
        findViewById<View>(R.id.list).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = when(view?.id) {
            R.id.fields -> Intent(this, FieldsActivity::class.java)
            R.id.list -> Intent(this, ListActivity::class.java)
            else -> null
        }

        startActivity(intent!!)
    }
}
package company.vk.Lesson06.presentationlayer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import company.vk.Lesson06.R

class MainActivity : AppCompatActivity(), OnClickListener {
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
package company.vk.Lesson06.presentationlayer.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import company.vk.Lesson06.R
import company.vk.Lesson06.ServiceLocator
import company.vk.Lesson06.businesslayer.ICalculator

class FieldsActivity : AppCompatActivity() {
	val calculator by lazy { ServiceLocator.inject<ICalculator>() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_fields)

		findViewById<View>(R.id.button).setOnClickListener {
			val field = findViewById<EditText>(R.id.field)
			val text = findViewById<TextView>(R.id.text)

			val value = field.text.toString()
			val result = calculator.calculate(value)

			text.text = result
		}
	}
}
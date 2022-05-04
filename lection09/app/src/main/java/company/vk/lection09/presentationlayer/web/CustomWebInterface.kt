package company.vk.lection09.presentationlayer.web

import android.app.Activity
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CustomWebInterface(val activity: Activity) {
	@JavascriptInterface
	fun showToast(message: String) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
	}

	@JavascriptInterface
	fun showDialog(message: String) {
		AlertDialog.Builder(activity)
			.setMessage(message)
			.show()
	}


	companion object {
		const val TAG = "CustomWebInterface"
	}
}
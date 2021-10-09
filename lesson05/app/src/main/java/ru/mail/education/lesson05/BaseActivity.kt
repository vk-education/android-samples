package ru.mail.education.lesson05

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.util.Rational
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import ru.mail.education.lesson05.databinding.ActivityMainBinding
import java.io.Serializable

open class BaseActivity : AppCompatActivity() {
    class CustomObject: Serializable {

    }

    class ParcObject() : Parcelable {
        constructor(parcel: Parcel) : this() {
            val string = parcel.readString()
        }

        override fun describeContents(): Int {
            TODO("Not yet implemented")
        }

        override fun writeToParcel(p0: Parcel, p1: Int) {
            TODO("Not yet implemented")
            p0.writeString("")
        }

        companion object CREATOR : Parcelable.Creator<ParcObject> {
            override fun createFromParcel(parcel: Parcel): ParcObject {
                return ParcObject(parcel)
            }

            override fun newArray(size: Int): Array<ParcObject?> {
                return arrayOfNulls(size)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate for $this")
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.time.text = System.currentTimeMillis().toString()
        binding.launchStandardActivity.setOnClickListener {
            val intent = Intent(this@BaseActivity, StandardActivity::class.java).apply {
                putExtra("PARAM1", CustomObject())
            }
            startActivity(intent)
        }

        binding.launchSingleTopActivity.setOnClickListener {
            val intent = Intent(this@BaseActivity, SingleTopActivity::class.java)
            startActivity(intent)
        }

        binding.launchSingleTaskActivity.setOnClickListener {
            val intent = Intent(this@BaseActivity, SingleTaskActivity::class.java)
            startActivity(intent)
        }

        binding.launchSingleInstanceActivity.setOnClickListener {
            val intent = Intent(this@BaseActivity, SingleInstanceActivity::class.java).apply {
                data = Uri.parse("education://HelloWorld/path1/path2?param1=&param2=yes#sfkjskjfkdsajfasdf")
            }
            Intent("ACTION").apply {

            }


            startActivity(intent)
        }

        binding.showPip.setOnClickListener {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(1, 1))
                .build()
            enterPictureInPictureMode(params)
        }


        val openDocumentContract = registerForActivityResult(OpenDocumentsContract()) {
            Toast.makeText(this, "onActivityResult(link: ${it})", Toast.LENGTH_LONG).show()
        }

        binding.openDocument.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }

            try {
                openDocumentContract.launch(emptyArray())
//                startActivityForResult(intent, 10001)
            } catch (e: Throwable) {
                Toast.makeText(this@BaseActivity, "Can't open document", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)

        Log.d(TAG, "onNewIntent for $this")
    }

    override fun onDestroy() {
        super.onDestroy()


        Log.d(TAG, "onDestroy for $this")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.d(TAG, "onConfigurationChanged for $this, newConfig: $newConfig")
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        Log.d(TAG, "onPictureInPictureModeChanged for $this, isInPictureInPictureMode=$isInPictureInPictureMode")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        Toast.makeText(this, "onActivityResult(link: ${data?.data})", Toast.LENGTH_LONG).show()
//    }


    companion object {
        const val TAG = "BaseActivity"
    }
}
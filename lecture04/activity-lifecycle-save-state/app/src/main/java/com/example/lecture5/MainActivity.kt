package com.example.lecture5

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener


class MainActivity : AppCompatActivity() {

    private var inputText: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val savedText = savedInstanceState?.getString("SampleKey")

        findViewById<TextView>(R.id.text).text = inputText
        findViewById<EditText>(R.id.edit_text).addTextChangedListener(
            onTextChanged = { charSeq, _, _, _ ->
                inputText = charSeq.toString()
            })
    }


    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString("SampleKey", "SampleValue")
        super.onSaveInstanceState(outState)
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        inputText = persistentState?.getString("PersistKey").orEmpty()
        super.onCreate(savedInstanceState, persistentState)
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outPersistentState.putString("PersistKey", inputText)
        log("outPersistentState: $outPersistentState")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged: $newConfig")
    }
}



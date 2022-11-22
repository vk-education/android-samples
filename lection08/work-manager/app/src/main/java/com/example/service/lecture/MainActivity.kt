package com.example.service.lecture

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var currentMsg: String = ""
    private var selectedRadioId: Int = R.id.simple_radio
    private var isForeground: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(this)
        findViewById<EditText>(R.id.msg_edittext).addTextChangedListener {
            currentMsg = it?.toString().orEmpty()
        }
        findViewById<CheckBox>(R.id.id_foreground_checkbox).setOnCheckedChangeListener { buttonView, isChecked ->
            isForeground = isChecked
        }

        findViewById<RadioGroup>(R.id.variant_group).setOnCheckedChangeListener { group, checkedId ->
            selectedRadioId = checkedId
        }
        findViewById<Button>(R.id.period_button).setOnClickListener {
            val request = PeriodicWorkRequestBuilder<WorkerTwo>(MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .addTag("periodic")
                .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                .build()

            workManager.enqueue(request)
        }
        findViewById<Button>(R.id.one_time_button).setOnClickListener {
            val request = when (selectedRadioId) {
                R.id.simple_radio -> {
                    OneTimeWorkRequestBuilder<WorkerOne>()
                        .addTag("simple_radio")
                        .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                        .build()
                }
                R.id.deferred_radio -> {
                    OneTimeWorkRequestBuilder<WorkerOne>()
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .addTag("deferred_radio")
                        .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                        .build()
                }
                R.id.expedited_radio -> {
                    OneTimeWorkRequestBuilder<WorkerOne>()
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .addTag("expedited_radio")
                        .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                        .build()
                }
                R.id.constraint_radio -> {

                    val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()

                    OneTimeWorkRequestBuilder<WorkerOne>()
                        .setConstraints(constraints)
                        .addTag("constraint_radio")
                        .build()
                }
                else -> null

            }

            request?.let {
                workManager.enqueue(it)
            }
        }


        findViewById<Button>(R.id.custom_button).setOnClickListener {
            val request1 = OneTimeWorkRequestBuilder<WorkerOne>()
                .addTag("request1")
                .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                .build()

            val request2 = OneTimeWorkRequestBuilder<WorkerTwo>()
                .addTag("request2")
                .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                .build()

            val request3 = OneTimeWorkRequestBuilder<WorkerThree>()
                .addTag("request3")
                .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                .build()

            val request4 = OneTimeWorkRequestBuilder<WorkerFour>()
                .addTag("request4")
                .setInputData(BaseWorker.getWorkData(currentMsg, isForeground))
                .build()

            workManager
                .beginWith(request1)
                .then(listOf(request2, request3))
                .then(request4)
                .enqueue()
        }
    }


}
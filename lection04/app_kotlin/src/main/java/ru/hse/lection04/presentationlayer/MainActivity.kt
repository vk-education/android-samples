package ru.hse.lection04.presentationlayer

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection04.R
import ru.hse.lection04.businesslayer.LogProvider
import ru.hse.lection04.businesslayer.ServiceLocator
import ru.hse.lection04.objects.LogEntry
import ru.hse.lection04.presentationlayer.adapter.LogAdapter

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }


    protected var mConnectivityProvider = ServiceLocator.connectivityProvider
    protected var mLogProvider = ServiceLocator.logProvider

    protected val mLogListener = LogListener()
    protected val mLogAdapter = LogAdapter()

    protected lateinit var mTrackConnectivity: SwitchCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настраиваем свитч, для изменения состояния фонового трекинга
        mTrackConnectivity = findViewById<SwitchCompat>(R.id.connectivity_track).apply {
            isChecked = mConnectivityProvider.isTrackEnabled
            setOnCheckedChangeListener(TrackerChangeListener())
        }

        // Настраиваем ресайклер, для отображения логов
        findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLogAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        // Подписываемся на обновление логов
        mLogProvider.register(mLogListener)
    }

    override fun onResume() {
        super.onResume()

        // При возврате фокуса, на всякий случай, проверяем состояние трекинга
        checkAndUpdateState()
    }

    override fun onStop() {
        // Отписываемся от обновления логов
        mLogProvider.unregister(mLogListener)

        super.onStop()
    }

    /**
     * Проверить и актуализировать состояние трекинга
     */
    protected fun checkAndUpdateState() {
        if (mTrackConnectivity.isChecked != mConnectivityProvider.isTrackEnabled) {
            mTrackConnectivity.isChecked = mConnectivityProvider.isTrackEnabled
        } else {
            updateServiceState(mConnectivityProvider.isTrackEnabled)
        }
    }

    /**
     * Стартануть или остановить трекинг
     * @param value Если true - то стартануть сервис. Иначе - застопить
     */
    protected fun updateServiceState(value: Boolean) {
        val intent: Intent = ConnectivityService.Companion.newInstance(applicationContext)
        if (value) {
            ContextCompat.startForegroundService(applicationContext, intent)
        } else {
            stopService(intent)
        }
    }

    /**
     * Вывести новый список логов
     * @param entries список сообщений лога
     */
    protected fun updateData(entries: List<LogEntry>?) {
        mLogAdapter.submitList(entries)
    }

    /**
     * Подписчик на обновление логгера
     */
    protected inner class LogListener : LogProvider.IListener {
        override fun logUpdated(provider: LogProvider?, entries: List<LogEntry>?) {
            updateData(entries)
        }
    }

    /**
     * Подписчик на изменение состояние свитча
     */
    protected inner class TrackerChangeListener : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            val needServiceUpdate = mConnectivityProvider.setTrackEnabled(isChecked)
            if (needServiceUpdate) {
                updateServiceState(isChecked)
            }
        }
    }
}
package company.vk.lesson08

import android.content.Context
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import java.util.concurrent.TimeUnit

class TimerWork(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        while (!isStopped) {
            delay(TimeUnit.SECONDS.toMillis(5))

            val lastTime = System.currentTimeMillis()
            val builder = Data.Builder().apply {
                putAll(inputData)
                putLong(EXTRAS_LAST_TIME, lastTime)
            }
            setProgress(builder.build())
        }

        return Result.success()
    }

    companion object {
        protected const val EXTRAS_START_TIME = "EXTRAS_START_TIME"
        protected const val EXTRAS_LAST_TIME = "EXTRAS_LAST_TIME"
        protected const val TAG = "TimerWork"

        fun schedule(context: Context, startTime: Long, scope: CoroutineScope) {
            val uuid = UUID.randomUUID()

            val dataBuilder = Data.Builder().apply {
                putLong(EXTRAS_START_TIME, startTime)
            }

            val constraints = Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }

            val requestBuilder = OneTimeWorkRequest.Builder(TimerWork::class.java).apply {
                setInitialDelay(5, TimeUnit.SECONDS)
                setInputData(dataBuilder.build())
                setConstraints(constraints.build())
                addTag(TAG)
                setId(uuid)
            }

            val manager = WorkManager.getInstance(context)
            manager.enqueue(requestBuilder.build())

            manager.getWorkInfoByIdFlow(uuid)
                .onEach { info ->
                    if (info.state != WorkInfo.State.RUNNING) {
                        return@onEach
                    }

                    val sTime = info.progress.getLong(EXTRAS_START_TIME, 0)
                    val lTime = info.progress.getLong(EXTRAS_LAST_TIME, 0)

                    Toast.makeText(context, "Progress delay: ${lTime - sTime}", Toast.LENGTH_SHORT).show()
                }
                .launchIn(scope)
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
        }
    }
}
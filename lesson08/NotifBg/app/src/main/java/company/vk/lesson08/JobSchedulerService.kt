package company.vk.lesson08

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.widget.Toast


class JobSchedulerService: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        if (params?.jobId == SIMPLE_JOB_ID) {
            val startTime = params.extras.getLong(EXTRAS_START_TIME)
            val delay = System.currentTimeMillis() - startTime
            Toast.makeText(applicationContext, "onStartJob with delay=$delay", Toast.LENGTH_LONG).show()
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        if (params?.jobId == SIMPLE_JOB_ID) {
            val startTime = params.extras.getLong(EXTRAS_START_TIME)
            val delay = System.currentTimeMillis() - startTime
            Toast.makeText(applicationContext, "onStopJob with delay=$delay", Toast.LENGTH_LONG).show()
        }

        return true
    }

    companion object {
        const val SIMPLE_JOB_ID = 10001

        protected const val EXTRAS_START_TIME = "EXTRAS_START_TIME"

        fun scheduleSimpleJob(context: Context, startTime: Long) {
            val component = ComponentName(context, JobSchedulerService::class.java)

            val arguments = PersistableBundle().apply {
                putLong(EXTRAS_START_TIME, startTime)
            }

            val builder = JobInfo.Builder(SIMPLE_JOB_ID, component).apply {
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                setExtras(arguments)
            }
            val job = builder.build()

            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.schedule(job)
        }

        fun unscheduleSimpleJob(context: Context) {
            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(SIMPLE_JOB_ID)
        }
    }
}
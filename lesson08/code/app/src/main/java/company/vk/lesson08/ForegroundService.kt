package company.vk.lesson08

import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import company.vk.lesson08.businesslayer.Music

class ForegroundService : MediaSessionService() {
    protected var session: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        val player = createPlayer()
        session = MediaSession.Builder(this, player).build()
        addSession(session!!)

        try {
            val notificationBuilder = NotificationCompat.Builder(this, Notifications.Channels.PLAYER.name).apply {
                setOngoing(true)
            }

            val foregroundType = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                else -> 0
            }
            ServiceCompat.startForeground(this, 100, notificationBuilder.build(), foregroundType)
        } catch (error: Throwable) {
            error.printStackTrace()
        }
    }

    override fun onDestroy() {
        session?.run {
            player.release()
            release()
            session = null
        }

        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getStringExtra(COMMAND)

        when (command) {
            COMMAND_START -> session?.player?.playWhenReady = true
            COMMAND_STOP -> session?.player?.playWhenReady = false
            COMMAND_DESTROY -> stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = session?.player
        if (player?.playWhenReady == true) {
            // Make sure the service is not in foreground.
            player.pause()
        }
        stopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return session
    }

    @OptIn(UnstableApi::class)
    protected fun createPlayer(): Player {
        val items = Music.Items.values().map { MediaItem.fromUri(it.path)  }
        val playerBuilder = ExoPlayer.Builder(this)
        return playerBuilder.build().apply {
            setMediaItems(items)
            playWhenReady = true
            prepare()
        }
    }

    companion object {
        protected const val COMMAND = "COMMAND"

        const val COMMAND_START = "COMMAND_START"
        const val COMMAND_STOP = "COMMAND_STOP"
        const val COMMAND_DESTROY = "COMMAND_DESTROY"

        fun executeCommand(context: Context, command: String) {
            val intent = Intent(context, ForegroundService::class.java).apply {
                putExtra(COMMAND, command)
            }

            ContextCompat.startForegroundService(context, intent)
        }
    }
}
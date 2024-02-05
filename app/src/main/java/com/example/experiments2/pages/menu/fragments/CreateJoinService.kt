package com.example.experiments2.pages.menu.fragments

import android.app.ActivityManager
import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import com.example.experiments2.R
import com.example.experiments2.constant.Constant.PACKAGE_NAME
import com.example.experiments2.network.FirebaseUtil
import com.example.experiments2.network.repository.RoomRepository
import com.google.firebase.FirebaseApp


class CreateJoinService : IntentService("FirebaseBackgroundService") {

    companion object {

        private const val MENU_ROOM_ID = "menu_room_id"
        private var handler: Handler? = null

        const val ACTION_STOP_SERVICE = "your.package.ACTION_STOP_SERVICE"


        fun startService(context: Context, roomId: String?) {
            val serviceIntent = Intent(context, CreateJoinService::class.java)
            serviceIntent.putExtra(MENU_ROOM_ID, roomId)

            context.startForegroundService(serviceIntent)
        }

        fun stopService(context: Context) {
            val stopServiceIntent = Intent(ACTION_STOP_SERVICE)
            context.sendBroadcast(stopServiceIntent)
        }
    }

    private val roomRepository = RoomRepository()
    private var roomId: String? = ""

    private val checkInterval = 1000L  // Check every 5 seconds (adjust as needed)
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_STOP_SERVICE -> {
                    handler?.removeCallbacksAndMessages(null)
                    handler = null
                    stopSelf()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelID = createNotificationChannel("my_service", "My Background Service")
        val notificationBuilder = NotificationCompat.Builder(this, channelID)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(1, notification)

        handler = Handler()

        val filter = IntentFilter().apply {
            addAction(ACTION_STOP_SERVICE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }

        val appCheckRunnable = object : Runnable {
            override fun run() {
                if (!isAppRunning()) {
                    try {
                        if (intent != null) {
                            FirebaseApp.initializeApp(applicationContext)

                            roomId = intent.getStringExtra(MENU_ROOM_ID) ?: ""
                            if (roomId != null) {
                                roomRepository.leaveRoom(
                                    applicationContext,
                                    roomId!!,
                                    FirebaseUtil.firebaseObserver(
                                        onDataRetrieved = {
                                            stopService(applicationContext)
                                            stopSelf()
                                            handler?.removeCallbacksAndMessages(null)
                                            handler = null
                                            handler?.removeCallbacksAndMessages(null)
                                        }
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
                println("service is running")
                handler?.postDelayed(this, checkInterval)
            }
        }

        handler?.postDelayed(appCheckRunnable, checkInterval)
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        unregisterReceiver(receiver)
        super.onTaskRemoved(rootIntent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        unregisterReceiver(receiver)
        return super.onUnbind(intent)
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {

    }

    fun isAppRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val recentTasks = activityManager.getRunningTasks(Int.MAX_VALUE)

        recentTasks.forEach { task ->
            val baseActivity = task.baseActivity

            if (baseActivity != null) {
                if (baseActivity.toShortString().contains(PACKAGE_NAME)) {
                    return true
                }
            }
        }

        return false
    }
}
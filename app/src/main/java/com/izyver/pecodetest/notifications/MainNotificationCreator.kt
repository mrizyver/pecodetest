package com.izyver.pecodetest.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.izyver.pecodetest.MainActivity
import com.izyver.pecodetest.R

class MainNotificationCreator(private val context: Context) : NotificationCreator {

    private val builder: NotificationCompat.Builder

    init {
        createChanel()
        builder = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.getString(R.string.notification_title))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    override fun create(number: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.action = ACTION_OPEN_SPECIFIC_SCREEN
        intent.putExtra(KEY_NUMBER_OF_SCREEN, number)
        val pendingIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentText(String.format(context.getString(R.string.notification_content_format), number))
                .setContentIntent(pendingIntent)
        val notificationManager= NotificationManagerCompat.from(context)
        notificationManager.notify(number, builder.build())
    }

    private fun createChanel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val name = context.getString(R.string.chanel_name)
        val description = context.getString(R.string.chanel_description)
        val channel = NotificationChannel(CHANEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANEL_ID = "main notifications"
        const val KEY_NUMBER_OF_SCREEN = "number_of_screen_from_notification"
        const val ACTION_OPEN_SPECIFIC_SCREEN = "com.izyver.pecodetest.OpenScreenAction"
    }
}
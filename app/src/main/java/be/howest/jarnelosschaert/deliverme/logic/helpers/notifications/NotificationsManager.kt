package be.howest.jarnelosschaert.deliverme.logic.helpers.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import be.howest.jarnelosschaert.deliverme.MainActivity
import be.howest.jarnelosschaert.deliverme.R
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NotificationsManager(private val context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val CHANNEL_ID = "DeliverMe"
        private const val CHANNEL_NAME = "DeliverMe"
        private const val CHANNEL_DESCRIPTION = "Default Notifications DeliverMe"
        private const val NOTIFICATION_ID = 1
        private const val PREFS_NAME = "NotificationsPrefs"
        private const val KEY_NOTIFICATIONS = "notifications"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(title: String, content: String, delivery: Delivery) {
        addNotification(Notification(content, delivery))
        val intent = Intent(context, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(intent)
        val pendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.delivery)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.delivery))
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun saveNotifications(notifications: List<Notification>) {
        val jsonString = Json.encodeToString(NotificationsWrapper(notifications))
        preferences.edit().putString(KEY_NOTIFICATIONS, jsonString).apply()
    }

    fun getNotifications(): List<Notification> {
        val jsonString = preferences.getString(KEY_NOTIFICATIONS, null)
        return if (jsonString != null) {
            val wrapper = Json.decodeFromString<NotificationsWrapper>(jsonString)
            wrapper.notifications
        } else {
            emptyList()
        }
    }

    private fun addNotification(notification: Notification) {
        val currentNotifications = getNotifications().toMutableList()
        currentNotifications.add(notification)
        saveNotifications(currentNotifications)
    }

    fun isLastMessageSame(message: String): Boolean {
        val currentNotifications = getNotifications()
        return if (currentNotifications.isNotEmpty()) {
            currentNotifications.last().message == message
        } else {
            false
        }
    }

    @Serializable
    private data class NotificationsWrapper(val notifications: List<Notification>)
}

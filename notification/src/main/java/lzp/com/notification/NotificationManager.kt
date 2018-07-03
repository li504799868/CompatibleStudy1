package lzp.com.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import java.util.ArrayList

/**
 * Created by li.zhipeng on 2018/7/3.
 *
 *      通知管理类
 */
object NotificationManager {

    /**
     * 渠道信息
     * */
    enum class Channel(val value: String) {

        /**
         * 渠道1
         * */
        CHANNEL1("test1"),

        /**
         * 渠道2
         * */
        CHANNEL2("test2")
    }

    /**
     * 发送一条通知
     * */
    fun sendNotification(context: Context, channel: Channel, notifyId: Int) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        // 配置通知的渠道信息
        // 判断是否是8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager.notificationChannels.isEmpty()) {
                initNotificationChannels(mNotificationManager)
            }
        }
        // 发送通知
        mNotificationManager.notify(notifyId, createNotification(context, channel.value))
    }

    /**
     * 创建一条通知
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannels(mNotificationManager: NotificationManager) {
        // 判断是否是8.0
        val channels = ArrayList<NotificationChannel>()
        // 这里配置了两个渠道，在实际使用中，请命名跟功能相关的channelID和channelName
        var channelID = Channel.CHANNEL1.value
        var channelName = "测试渠道1"
        // 指定该渠道的通知的重要性，如果是重要性比较低，可能会被折叠
        var channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        //一般使用的notification
        channels.add(channel)
        channelID = Channel.CHANNEL2.value
        channelName = "测试渠道2"
        // 指定该渠道的通知的重要性，如果是重要性比较低，可能会被折叠
        channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_MIN)
        channels.add(channel)
        // 把渠道添加到NotificationManager中
        mNotificationManager.createNotificationChannels(channels)
    }

    /**
     * 创建一条通知
     * */
    private fun createNotification(context: Context, channelId: String): Notification {
        // 第二个参数就是指定channelId
        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(channelId)
                .setContentText(channelId)
                .setTicker(channelId)
                .setAutoCancel(true).priority = NotificationCompat.PRIORITY_HIGH
        return builder.build()
    }

}
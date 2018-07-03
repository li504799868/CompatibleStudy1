package lzp.com.notification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private var notifyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button1).setOnClickListener {
            NotificationManager.sendNotification(this@MainActivity, NotificationManager.Channel.CHANNEL1, notifyId++)
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            NotificationManager.sendNotification(this@MainActivity, NotificationManager.Channel.CHANNEL2, notifyId++)
        }
    }
}

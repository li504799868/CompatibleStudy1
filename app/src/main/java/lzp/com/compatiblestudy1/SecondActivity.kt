package lzp.com.compatiblestudy1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // 延迟一秒销毁页面，返回新的文字
        Handler().postDelayed({
            val intent = Intent()
            intent.putExtra("text", "second back")
            setResult(0, intent)
            finish()
        }, 1000)


    }
}

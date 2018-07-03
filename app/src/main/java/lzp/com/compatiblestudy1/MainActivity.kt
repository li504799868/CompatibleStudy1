package lzp.com.compatiblestudy1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button).setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, SecondActivity::class.java), 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 得到添加的所有的fragment，循环调用每一个fragment的onActivityResult方法
        // 这里需要对fragment进行空判断，因为移除的fragment可能会占用一个位置，值为null
        val fm = supportFragmentManager
        for (item in fm.fragments.filter { it != null}) {
            item.onActivityResult(requestCode, resultCode, data)
        }

    }
}

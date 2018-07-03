package com.lzp.scrollview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MyScrollview>(R.id.scrollView).setOnScrollToBottomLintener {
            // 如果已经滚动到底部，弹出提示
            if (it){
                Toast.makeText(this@MainActivity, "已经滚动到底部", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

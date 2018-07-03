package com.lzp.netmonitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() , NetworkBroadCast.NetEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkBroadCast.addNetWorkHandler(this)
    }

    override fun onNetChange(netWorkType: Int) {
        Log.e("lzp", netWorkType.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        NetworkBroadCast.removeNetWorkHandler(this)
    }
}

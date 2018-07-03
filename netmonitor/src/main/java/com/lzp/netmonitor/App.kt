package com.lzp.netmonitor

import android.app.Application

/**
 * Created by li.zhipeng on 2018/6/28.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // 注册网络监听
        NetworkBroadCast.register(this)
    }
}
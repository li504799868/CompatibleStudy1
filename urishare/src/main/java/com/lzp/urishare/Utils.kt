package com.lzp.urishare

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by li.zhipeng on 2018/6/29.
 *
 *  工具类
 */
object Utils {

    const val REQUEST_CODE = 100

    /**
     * 打开拍照
     */
    fun takePhoto(activity: Activity, file: File) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val timeStampFormat = SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss", Locale.US)
        val filename = timeStampFormat.format(Date())
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)

        // 打印一下Uri
        Log.e("lzp", FileProvider.getUriForFile(activity, activity.packageName + ".update.fileProvider", file).toString())

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(activity, activity.packageName + ".update.fileProvider", file))
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

}
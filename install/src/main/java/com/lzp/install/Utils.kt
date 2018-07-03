package com.lzp.install

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import java.io.File

/**
 * Created by li.zhipeng on 2018/6/29.
 */
object Utils {


    /**
     * 安装app
     */
    fun installApk(context: Context, file: File) {
        // 如果是8.0手机，需要判断权限
        if (checkCanInstallUnknownApk(context)) {
            installApkWithoutPermission(context, file)
        } else {
            // 申请安装权限
            AndPermission.with(context)
                    .permission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
                    .callback(object : PermissionListener {
                        override fun onSucceed(requestCode: Int, grantPermissions: List<String>) {
                            installApkWithoutPermission(context, file)
                        }

                        override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
                            Toast.makeText(context, "未授予安装应用权限", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .start()
        }
    }

    /**
     * 是否允许安装未知来源的应用
     */
    private fun checkCanInstallUnknownApk(context: Context): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O || context.packageManager.canRequestPackageInstalls()
    }

    private fun installApkWithoutPermission(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // 适配android7.0 ，不能直接访问原路径
        // 需要对intent 授权
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(FileProvider.getUriForFile(context, context.packageName + ".update.fileProvider", file),
                "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

}
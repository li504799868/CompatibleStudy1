package com.lzp.urishare

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndPermission.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callback(object : PermissionListener{
                    override fun onSucceed(requestCode: Int, grantPermissions: MutableList<String>) {
                        file = File("${Environment.getExternalStorageDirectory()}/share/test.jpg ")
                        file.parentFile.mkdirs()
                        camera.setOnClickListener {
                            Utils.takePhoto(this@MainActivity, file)
                        }
                    }

                    override fun onFailed(requestCode: Int, deniedPermissions: MutableList<String>) {
                    }

                })
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Utils.REQUEST_CODE) {
            image.setImageURI(Uri.fromFile(file))
        }
    }
}

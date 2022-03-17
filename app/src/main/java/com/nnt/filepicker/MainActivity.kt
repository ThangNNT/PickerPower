package com.nnt.filepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.nnt.filepicker.imagepicker.GalleryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tedPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    /**
     *  Permission request
     *
     * */
    private fun tedPermission(vararg permissions: String) {
        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setPermissions(*permissions)
            .setDeniedMessage("Nếu bạn từ chối cấp phép, bạn không thể sử dụng dịch vụ này. Vui lòng bật quyền tại [Cài đặt] > [Quyền]")
            .check()
    }


    /**
     *  Permission listener
     * */
    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            this@MainActivity.startActivity(GalleryActivity.newIntent(this@MainActivity))
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

        }
    }
}
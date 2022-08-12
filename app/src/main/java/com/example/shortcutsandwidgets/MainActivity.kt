package com.example.shortcutsandwidgets

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.shortcutsandwidgets.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        createShortcut()
        createPinnedShortcut()
    }

    private fun createShortcut() {
        vBinding.btnShortcut.setOnClickListener {
            val shortcut = ShortcutInfoCompat.Builder(this, "youtube")
                .setShortLabel("Youtube")
                .setLongLabel("Abrir Youtube")
                .setIcon(IconCompat.createWithResource(this, R.drawable.ic_baseline_slow_motion_video_24))
                .setIntent(
                    Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/"))
                )
                .build()
            try {
                ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
                Snackbar.make(vBinding.root, "Shortcut creado", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun createPinnedShortcut(){
        vBinding.btnPinnedShortcut.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 28) {
                val shortcutManager = getSystemService(ShortcutManager::class.java)

                if (shortcutManager!!.isRequestPinShortcutSupported) {

                    val pinShortcutInfo = ShortcutInfo.Builder(this, "pinned")
                        .setShortLabel("Youtube")
                        .setLongLabel("Abrir Youtube")
                        .setIntent(
                            Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/"))
                        )
                        .setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_push_pin_24))
                        .build()

                    val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)

                    val successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                        pinnedShortcutCallbackIntent, /* flags */ PendingIntent.FLAG_MUTABLE)

                    shortcutManager.requestPinShortcut(pinShortcutInfo,
                        successCallback.intentSender)
                }
            }
        }
    }
}
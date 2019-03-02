package com.kyujin.assistantopener

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val googlePackageName = "com.google.android.googlequicksearchbox"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val defaultActivity = sharedPrefs.getString("defaultAction", "VoiceSearchActivity")
        val intent = Intent()
        intent.component = ComponentName(googlePackageName, "$googlePackageName.$defaultActivity")
        startActivity(intent)
        finish()
    }
}

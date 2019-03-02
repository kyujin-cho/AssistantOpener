package com.kyujin.assistantopener

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val packageName = sharedPrefs.getString("defaultPackage", "com.google.android.googlequicksearchbox") ?: "com.google.android.googlequicksearchbox"
        val activityName = sharedPrefs.getString("defaultActivity", "VoiceSearchActivity") ?: "VoiceSearchActivity"

        val intent = Intent()
        intent.component = ComponentName(packageName, "$packageName.$activityName")
        startActivity(intent)
        finish()
    }
}

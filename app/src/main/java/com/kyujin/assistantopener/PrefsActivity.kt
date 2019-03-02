package com.kyujin.assistantopener

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.content.pm.PackageManager
import android.view.View


class PrefsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs)

        val group = findViewById<RadioGroup>(R.id.searchRadioGroup)
        val searchButton = findViewById<RadioButton>(R.id.searchRadioButton)
        val voiceButton = findViewById<RadioButton>(R.id.voiceRadioButton)
        val assistantButton = findViewById<RadioButton>(R.id.assistantRadioButton)

        if (!packageExists("com.google.android.apps.googleassistant")) {
            assistantButton.visibility = View.GONE
        }

        val prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val defaultAction = prefs.getString("defaultActivity", "") ?: ""

        when (defaultAction) {
            "com.google.android.googlequicksearchbox.VoiceSearchActivity" -> voiceButton.isChecked = true
            "com.google.android.googlequicksearchbox.SearchActivity" -> searchButton.isChecked = true
            "com.google.android.apps.googleassistant.AssistantActivity" -> assistantButton.isChecked = true
            else -> {
                updateSettings("com.google.android.googlequicksearchbox.VoiceSearchActivity", prefs)
                voiceButton.isChecked = true
            }
        }

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            when (group.checkedRadioButtonId) {
                R.id.searchRadioButton -> updateSettings("com.google.android.googlequicksearchbox.SearchActivity", prefs)
                R.id.voiceRadioButton -> updateSettings("com.google.android.googlequicksearchbox.VoiceSearchActivity", prefs)
                R.id.assistantRadioButton -> updateSettings("com.google.android.apps.googleassistant.AssistantActivity", prefs)
            }

            Snackbar.make(group, getString(R.string.pref_saved), Snackbar.LENGTH_SHORT).show()
        }
    }

    fun updateSettings(action: String, prefs: SharedPreferences) {
        val editor = prefs.edit()
        val packageNameArr = action.split(".")
        val packageStr = packageNameArr.subList(0, packageNameArr.size - 1).joinToString(".")
        val activityName = packageNameArr[packageNameArr.size - 1]
        editor.putString("defaultActivity", activityName)
        editor.putString("defaultPackage", packageStr)
        editor.apply()
    }

    fun packageExists(targetPackage: String): Boolean {
        val pm = packageManager
        try {
            val info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

        return true
    }
}

package com.kyujin.assistantopener

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup

class PrefsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs)

        val group = findViewById<RadioGroup>(R.id.searchRadioGroup)
        val searchButton = findViewById<RadioButton>(R.id.searchRadioButton)
        val voiceButton = findViewById<RadioButton>(R.id.voiceRadioButton)

        val prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val defaultAction = prefs.getString("defaultAction", "")

        when (defaultAction) {
            "VoiceSearchActivity" -> voiceButton.isChecked = true
            "SearchActivity" -> searchButton.isChecked = true
            else -> {
                updateSettings("VoiceSearchActivity", prefs)
                voiceButton.isChecked = true
            }
        }

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            when (group.checkedRadioButtonId) {
                R.id.searchRadioButton -> updateSettings("SearchActivity", prefs)
                R.id.voiceRadioButton -> updateSettings("VoiceSearchActivity", prefs)
            }

            Snackbar.make(group, getString(R.string.pref_saved), Snackbar.LENGTH_SHORT).show()
        }
    }

    fun updateSettings(action: String, prefs: SharedPreferences) {
        val editor = prefs.edit()
        editor.putString("defaultAction",action)
        editor.apply()
    }
}

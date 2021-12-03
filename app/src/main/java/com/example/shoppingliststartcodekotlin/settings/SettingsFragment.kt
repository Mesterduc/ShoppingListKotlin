package com.example.shoppingliststartcodekotlin.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.shoppingliststartcodekotlin.R

class SettingsFragment : PreferenceFragmentCompat()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
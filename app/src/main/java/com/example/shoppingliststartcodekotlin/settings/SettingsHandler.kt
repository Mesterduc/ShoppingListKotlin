package com.example.shoppingliststartcodekotlin.settings

import android.content.Context
import androidx.preference.PreferenceManager

object SettingsHandler {
    private const val SETTINGS_COLOR = "color"

    //note these are "static" methods - meaning they always exists
    //so we do not have to create an instance of this class to
    //get the values - the Singleton pattern again!

    fun getColor(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SETTINGS_COLOR, "#0000FF")!!
    }
}
package com.astro.test.irwan.utils

import android.content.Context
import com.astro.test.irwan.utils.Constant.KEY_PREFERENCE_NAME
import com.astro.test.irwan.utils.Constant.KEY_SORT
import com.astro.test.irwan.utils.Constant.KEY_TOKEN

class PreferenceManager(context: Context) {

    companion object {
        private lateinit var prefInstance: PreferenceManager

        @Synchronized
        fun getPref(context: Context): PreferenceManager {
            prefInstance = PreferenceManager(context)
            return prefInstance
        }
    }

    private val sharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    private fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    private fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    fun clearPreference() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun clearSinglePreference(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    var prefToken: String?
        get() {
            getString(KEY_TOKEN)
            return sharedPreferences.getString(KEY_TOKEN, "")
        }
        set(value) {
            if (value != null) {
                putString(KEY_TOKEN, value)
            }
        }

    var prefSortAsc: Boolean
        get() {
            getBoolean(KEY_SORT)
            return sharedPreferences.getBoolean(KEY_SORT, true)
        }
        set(value) {
            putBoolean(KEY_SORT, value)
        }
}
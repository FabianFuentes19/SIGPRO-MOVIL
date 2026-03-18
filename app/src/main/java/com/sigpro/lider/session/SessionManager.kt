package com.sigpro.lider.session

import android.content.Context
import android.content.SharedPreferences
import com.sigpro.lider.SigproLiderApp

object SessionManager {

    private const val PREF_NAME = "sigpro_prefs"
    private const val KEY_TOKEN = "token"
    private const val KEY_ROL = "rol"

    private val prefs: SharedPreferences by lazy {
        val context: Context = SigproLiderApp.getAppContext()
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSession(token: String, rol: String?) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_ROL, rol)
            .apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getRol(): String? = prefs.getString(KEY_ROL, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}


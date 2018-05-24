package com.example.darli.tarefas.Util

import android.content.Context
import android.content.SharedPreferences

class PreferenciaSeguranca (context: Context) {
    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences("Tasks", Context.MODE_PRIVATE)
        //instancia uma tarefa do tipo SharePreferences

    fun storeString(key: String, value: String) {
        this.mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key: String): String {
        return this.mSharedPreferences.getString(key, "")
    }

    fun removeStoredString (key: String) {
        this.mSharedPreferences.edit().remove(key).commit()
    }
}
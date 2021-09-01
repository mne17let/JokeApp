package com.example.jokeapp.data

import android.content.Context
import com.example.jokeapp.R

interface ErrorResourceManager {
    fun getErrorString(stringID: Int): String
}

class BaseErrorResourceManager(private val context: Context): ErrorResourceManager{
    override fun getErrorString(stringID: Int): String {
        return context.getString(stringID)
    }
}
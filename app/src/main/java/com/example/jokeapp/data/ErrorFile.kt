package com.example.jokeapp.data

import com.example.jokeapp.R

interface JokeDownloadError{
    fun getErrorMessage(): String
}

class ErrorNoConnection(private val resourceManager: ErrorResourceManager): JokeDownloadError{
    override fun getErrorMessage(): String {
        return resourceManager.getErrorString(R.string.text_error_no_connection)
    }
}

class ServerError(private val resourceManager: ErrorResourceManager): JokeDownloadError{
    override fun getErrorMessage(): String {
        return resourceManager.getErrorString(R.string.text_error_server)
    }
}
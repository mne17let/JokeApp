package com.example.jokeapp.data.dataSources.cloud

import com.example.jokeapp.data.api.JokeModelJSON
import com.example.jokeapp.data.dataSources.CloudCallback

interface CloudDataSource {
    suspend fun getJokeFromCloud(): JokeCloudDataSource.Result
}
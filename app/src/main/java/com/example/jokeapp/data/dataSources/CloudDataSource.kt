package com.example.jokeapp.data.dataSources

interface CloudDataSource {
    fun getJokeFromCloud(callback: CloudCallback)
}
package com.example.jokeapp

import android.app.Application
import com.example.jokeapp.data.BaseErrorResourceManager
import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.JokeDownloadError
import com.example.jokeapp.data.TestModel
import com.example.jokeapp.viewmodel.ViewModel

class MyApplication: Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = ViewModel(TestModel(BaseErrorResourceManager(this)))
    }
}
package com.example.jokeapp

import android.app.Application
import com.example.jokeapp.data.*
import com.example.jokeapp.viewmodel.ViewModel
import com.google.gson.Gson

class MyApplication: Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = ViewModel(TestModel(BaseJokeLoader(Gson()), BaseErrorResourceManager(this)))
    }
}
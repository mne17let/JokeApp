package com.example.jokeapp

import android.app.Application
import com.example.jokeapp.data.*
import com.example.jokeapp.data.dataSources.CloudDataSource
import com.example.jokeapp.data.dataSources.JokeCacheDataSource
import com.example.jokeapp.data.dataSources.JokeCloudDataSource
import com.example.jokeapp.data.dataSources.database.CachedDataBase
import com.example.jokeapp.data.repository.JokeRepository
import com.example.jokeapp.viewmodel.ViewModel
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()

        val realm = Realm.init(this)

        val gson = GsonConverterFactory.create()

        val retrofit = Retrofit
            .Builder()
            .baseUrl("http://vk.com")
            .addConverterFactory(gson)
            .build()

        val retrofitJokeLoader = retrofit.create(RetrofitJokeLoader::class.java)

        viewModel = ViewModel(JokeRepository(JokeCloudDataSource(retrofitJokeLoader),
            CachedDataBase(Realm.getDefaultInstance()),
            BaseErrorResourceManager(this)))
    }
}
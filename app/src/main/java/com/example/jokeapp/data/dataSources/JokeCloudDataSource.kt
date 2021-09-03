package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.RetrofitJokeLoader
import com.example.jokeapp.data.api.JokeModelJSON
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

class JokeCloudDataSource(private val loader: RetrofitJokeLoader): CloudDataSource {
    override fun getJokeFromCloud(callback: CloudCallback) {
        loader.getJoke().enqueue(object : retrofit2.Callback<JokeModelJSON>{
            override fun onResponse(call: Call<JokeModelJSON>, response: Response<JokeModelJSON>) {
                if(response.isSuccessful){
                    callback.onSuccess(response.body()!!.toJoke())
                } else{
                    callback.onError(ErrorType.SERVICE_UNAVAILABLE)
                }
            }

            override fun onFailure(call: Call<JokeModelJSON>, t: Throwable) {
                if(t is UnknownHostException){
                    callback.onError(ErrorType.NO_CONNECTION)
                } else{
                    callback.onError(ErrorType.SERVICE_UNAVAILABLE)
                }
            }
        })
    }

    /*var count = 0

    override fun getJokeFromCloud(callback: CloudCallback) {
        callback.onSuccess(JokeModelJSON(0, "Тип шутки", "Шутка номер $count", "тестовый панч"))

        count++
    }*/
}
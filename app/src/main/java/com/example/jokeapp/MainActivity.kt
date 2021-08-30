package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.jokeapp.viewmodel.TextCallback
import com.example.jokeapp.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setProgressBar()
        setButton()
        startViewModel()
    }

    fun init(){
        button = findViewById(R.id.id_button)
        textView = findViewById(R.id.id_textview)
        progressBar = findViewById(R.id.id_progressbar)

        val myApplication = application as MyApplication
        viewModel = myApplication.viewModel
    }

    fun setButton(){
        button.setOnClickListener {
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE
            viewModel.getJoke()
        }
    }

    fun setProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    fun startViewModel(){
        val textCallback = object : TextCallback {
            override fun updateText(text: String) {
                doOnUiThread(text)
            }
        }

        viewModel.start(textCallback)
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }

    fun getRunnableForUpdateUI(text: String): Runnable{
        val newRunnable = object : Runnable{
            override fun run() {
                button.isEnabled = true
                progressBar.visibility = View.INVISIBLE
                textView.text = text
            }
        }

        return newRunnable
    }

    fun doOnUiThread(text: String){
        val newRunnable = getRunnableForUpdateUI(text)

        runOnUiThread(newRunnable)
    }
}
package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.jokeapp.viewmodel.JokeDataCallback
import com.example.jokeapp.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var viewModel: ViewModel
    private lateinit var likeIconButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setProgressBar()
        setButton()
        startViewModel()
        setCheckBox()
        setIconButton()
    }

    fun init(){
        button = findViewById(R.id.id_button)
        textView = findViewById(R.id.id_textview)
        progressBar = findViewById(R.id.id_progressbar)
        checkBox = findViewById(R.id.id_checkbox)
        likeIconButton = findViewById(R.id.id_like_button)

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
        val textCallback = object : JokeDataCallback {
            override fun updateText(text: String) {
                doOnUiThread(text)
            }

            override fun updateIcon(iconId: Int) {
                likeIconButton.setImageResource(iconId)
            }
        }

        viewModel.start(textCallback)
    }

    fun setIconButton(){
        likeIconButton.setOnClickListener {
            viewModel.changeCurrentJokeStatus()
        }
    }

    fun setCheckBox(){

        val listener = object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                viewModel.chooseOnlyFavourite(isChecked)
            }
        }

        checkBox.setOnCheckedChangeListener(listener)
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
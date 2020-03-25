package pl.michalboryczko.exercise.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseActivity
import pl.michalboryczko.exercise.model.api.call.LoginCall
import pl.michalboryczko.exercise.model.base.Status
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel>() {


    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerListeners()
    }

    fun registerListeners(){
        viewModel.sentences.observe(this, Observer {sentences->
            //Timber.d("sentences ${sentences[0].english} ${sentences[0].spanish}")
        })
        viewModel.words.observe(this, Observer {words->
            //Timber.d("words ${words[0].english} ${words[0].spanish}")
        })
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

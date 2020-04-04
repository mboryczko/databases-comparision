package pl.michalboryczko.exercise.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseActivity
import androidx.fragment.app.Fragment
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningFragment
import pl.michalboryczko.exercise.ui.markwords.MarkWordsFragment
import pl.michalboryczko.exercise.ui.search.SearchFragment
import pl.michalboryczko.exercise.ui.settings.SettingsFragment
import pl.michalboryczko.exercise.ui.test.TestFragment


class MainActivity : BaseActivity<MainViewModel>() {


    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerListeners()
        initialize()
    }

    fun registerListeners(){
        viewModel.sentences.observe(this, Observer {sentences->
            //Timber.d("sentences ${sentences[0].english} ${sentences[0].spanish}")
        })
        viewModel.words.observe(this, Observer {words->
            //Timber.d("wordsToLearn ${wordsToLearn[0].english} ${wordsToLearn[0].spanish}")
        })
    }

    fun initialize(){
        bottomNavigationView.setOnNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.menu_home -> openFragment(WordsLearningFragment())
                R.id.menu_test -> openFragment(TestFragment())
                R.id.menu_search -> openFragment(SearchFragment())
                R.id.menu_todo -> openFragment(MarkWordsFragment())
            }

            true
        }
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

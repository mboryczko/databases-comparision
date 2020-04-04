package pl.michalboryczko.exercise.ui.learnwords


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.words_learning_fragment.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment

class WordsLearningFragment : BaseFragment<WordsLearningViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.words_learning_fragment, container, false)
    }



    override fun onResume() {
        super.onResume()

        nextButtton.setOnClickListener {
            spanishWordTextView.visibility = View.GONE
            viewModel.getNextWord()
        }

        wordLayout.setOnClickListener {
            spanishWordTextView.visibility = if(spanishWordTextView.visibility == View.GONE) View.VISIBLE else View.GONE

            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(wordLayout)
        }

        viewModel.currentTranslation.observe(this, Observer {
            englishWordTextView.text = it.english
            spanishWordTextView.text = it.spanish

        })
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

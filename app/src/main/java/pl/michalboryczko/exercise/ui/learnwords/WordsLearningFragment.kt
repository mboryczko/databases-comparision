package pl.michalboryczko.exercise.ui.learnwords


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        getWordsRoomButton.setOnClickListener { viewModel.getWordsFromDb() }
        saveWordsRoomButton.setOnClickListener { viewModel.saveWordsToDb() }


        wordLayout.setOnClickListener {

            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(wordLayout)
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

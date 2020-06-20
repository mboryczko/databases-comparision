package pl.michalboryczko.exercise.ui.test


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.fragment_test.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningViewModel
import pl.michalboryczko.exercise.ui.search.SearchFragment


class TestFragment : BaseFragment<WordsLearningViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    private fun hideAnswear(){
        spanishWordTextView.visibility = View.GONE
    }

    private fun showAnswear(){
        spanishWordTextView.visibility = View.VISIBLE
    }

    private fun flipAnimation(){
        YoYo.with(Techniques.FlipInX)
                .duration(700)
                .playOn(wordLayout)
    }

    private fun slideAnimation(){
        YoYo.with(Techniques.SlideInRight)
                .duration(700)
                .playOn(wordLayout)
    }


    private fun onResult(colorRes: Int){
        showAnswear()
        flipAnimation()
        nextButton.setBackgroundColor(colorRes)

        checkButton.visibility = View.GONE
        nextButton.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        checkButton.setOnClickListener {
            viewModel.onCheckClicked(wordEditText.text.toString())
        }

        nextButton.setOnClickListener {
            checkButton.visibility = View.VISIBLE
            nextButton.visibility = View.GONE
            wordEditText.setText("")
            hideAnswear()
            slideAnimation()
            viewModel.getNextWord()
        }

        viewModel.showNoLearningWordsDialog.observe(this, Observer {
            MaterialDialog(requireContext()).show {
                title (R.string.no_learning_words )
                cancelOnTouchOutside(false)
                positiveButton(R.string.ok) {
                    mainInterface?.openSpecificFragment(SearchFragment())
                }
            }
        })

        viewModel.correctAnswear.observe(this, Observer {
           onResult(resources.getColor(R.color.green))
        })

        viewModel.wrongAnswear.observe(this, Observer {
            onResult(resources.getColor(R.color.red))
        })

        viewModel.currentTranslation.observe(this, Observer {
            englishWordTextView.text = it.english
            spanishWordTextView.text = it.spanish
        })
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

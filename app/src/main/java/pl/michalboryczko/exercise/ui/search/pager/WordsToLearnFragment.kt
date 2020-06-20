package pl.michalboryczko.exercise.ui.search.pager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recycler.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.ui.search.SearchAdapter
import pl.michalboryczko.exercise.ui.search.SearchViewModel

class WordsToLearnFragment : BaseFragment<SearchViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onResume() {
        super.onResume()

        viewModel.wordsToLearn.observe(this, Observer {event->
            event.getContentIfNotHandled()?.let { showWords(it) }
        })
    }

    private fun showWords(words: List<Translate>){
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = SearchAdapter(words, requireContext())
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

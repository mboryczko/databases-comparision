package pl.michalboryczko.exercise.ui.markwords


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_mark_words.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.model.presentation.MarkTranslate
import pl.michalboryczko.exercise.ui.search.SearchViewModel
import timber.log.Timber

class MarkWordsFragment : BaseFragment<SearchViewModel>() {

    private var adapter: MarkWordsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mark_words, container, false)
    }

    override fun onResume() {
        super.onResume()

        selectAllButton.setOnClickListener { adapter?.selectAll() }
        addToLearningListButton.setOnClickListener {
            val list = adapter?.getSelectedItems()
            list?.let { viewModel.addToLearningList(it) }
        }

        deleteWordsButton.setOnClickListener {
            val list = adapter?.getSelectedItems()
            list?.let { viewModel.deleteWords(it) }
        }

        viewModel.allWords.observe(this, Observer {event->
            event.getContentIfNotHandled()?.let { showWords(it) }
        })
    }

    private fun showWords(words: List<MarkTranslate>){
        val a =  MarkWordsAdapter(words, requireContext())
        adapter = a
        searchWordsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = a
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

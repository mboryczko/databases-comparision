package pl.michalboryczko.exercise.ui.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.model.presentation.Translate

class SearchFragment : BaseFragment<SearchViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChanged(searchEditText.text.toString())
            }
        })

        viewModel.words.observe(this, Observer {
            showWords(it)
        })
    }

    private fun showWords(words: List<Translate>){
        searchWordsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = SearchAdapter(words, requireContext())
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

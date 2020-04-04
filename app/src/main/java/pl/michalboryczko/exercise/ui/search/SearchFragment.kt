package pl.michalboryczko.exercise.ui.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.ui.search.pager.SearchPagerAdapter

class SearchFragment : BaseFragment<SearchViewModel>() {

    private lateinit var searchPager: SearchPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    fun initialize(){
        tabLayout.setupWithViewPager(pager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchPager = SearchPagerAdapter(childFragmentManager)
        pager.adapter = searchPager
        tabLayout.setupWithViewPager(pager)
    }

    override fun onResume() {
        super.onResume()
        initialize()

        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChanged(searchEditText.text.toString(), pager.currentItem == 0)
            }
        })
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

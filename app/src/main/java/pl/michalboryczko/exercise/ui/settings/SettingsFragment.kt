package pl.michalboryczko.exercise.ui.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_settings.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.source.repository.UserRepositoryImpl


class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    val values = listOf(UserRepositoryImpl.DATABASE_IMPL.ROOM, UserRepositoryImpl.DATABASE_IMPL.REALM, UserRepositoryImpl.DATABASE_IMPL.OBJECTBOX, UserRepositoryImpl.DATABASE_IMPL.GREENDAO)

    override fun onResume() {
        super.onResume()


        deleteAllWords.setOnClickListener { viewModel.deleteAllWords() }
        parseWords.setOnClickListener { viewModel.parseFirstNWords(wordsEditText.text.toString().toInt() * 1000) }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, values)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        databaseSpinner.adapter = adapter

        databaseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = values[position]
                viewModel.setDatabase(item)
            }
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

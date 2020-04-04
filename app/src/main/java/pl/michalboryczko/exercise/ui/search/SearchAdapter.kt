package pl.michalboryczko.exercise.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.translation_item.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.presentation.Translate


class SearchAdapter(
        private var items : List<Translate>,
        private val context: Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.translation_item, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            englishTextView.text = item.english
            spanishTextView.text = item.spanish
            numberTextView.text = (position + 1).toString()
        }
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val englishTextView = view.englishTextView
        val spanishTextView = view.spanishTextView
        val numberTextView = view.numberTextView

        init {

        }
    }
}

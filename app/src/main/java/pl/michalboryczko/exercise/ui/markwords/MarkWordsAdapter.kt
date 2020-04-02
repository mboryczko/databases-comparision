package pl.michalboryczko.exercise.ui.markwords

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mark_words_item.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.presentation.MarkTranslate
import timber.log.Timber


class MarkWordsAdapter(
        private var items : List<MarkTranslate>,
        private val context: Context) : RecyclerView.Adapter<MarkWordsAdapter.ViewHolder>() {

    fun selectAll(){
        items.forEach { it.isMarked = true }
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<MarkTranslate>{
        return items.filter { it.isMarked }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.mark_words_item, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            englishTextView.text = item.translate.english
            spanishTextView.text = item.translate.spanish
            wordCheckbox.isChecked = item.isMarked
        }
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val englishTextView = view.englishTextView
        val spanishTextView = view.spanishTextView
        val wordCheckbox = view.wordCheckbox
        val wholeRow = view.wholeRow

        init {
            wordCheckbox.setOnClickListener{
                val item = items[layoutPosition]
                item.isMarked = !item.isMarked
                notifyItemChanged(layoutPosition)
            }
        }
    }
}

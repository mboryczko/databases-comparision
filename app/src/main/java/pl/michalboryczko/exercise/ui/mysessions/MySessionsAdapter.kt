package pl.michalboryczko.exercise.ui.mysessions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.session_item.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.api.Session

interface OnSessionClickedListener{
    fun sessionClicked(session: Session)
}



class MySessionsAdapter(val items : List<Session>, val context: Context, val sessionClickedListener: OnSessionClickedListener) : RecyclerView.Adapter<MySessionsAdapter.ViewHolder>() {



    override fun getItemCount(): Int = items.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySessionsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.session_item, parent, false))
    }


    override fun onBindViewHolder(holder: MySessionsAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.sessionTextView.text = item.name
    }


    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val sessionTextView = view.sessionTextView
        val wholeRow = view.wholeRow

        init {
            wholeRow.setOnClickListener {
                sessionClickedListener.sessionClicked(items[layoutPosition])
            }
        }



    }
}
package pl.michalboryczko.exercise.ui.activesession

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.estimation_item.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.api.Estimation


interface OnEstimationChoosenListener{
    fun estimationChoosen(estimation: String)
}

class EstimationsToChooseAdapter(val items : List<String>, val context: Context, val listener: OnEstimationChoosenListener? = null) : RecyclerView.Adapter<EstimationsToChooseAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.estimation_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pointsEstimation.text = items[position]
        holder.usernameEstimation.visibility = View.GONE
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val pointsEstimation = view.pointsEstimationItem
        val usernameEstimation = view.usernameEstimationItem
        val wholeRow = view.wholeRow

        init {
            wholeRow.setOnClickListener {
                listener?.estimationChoosen(items[adapterPosition])
            }
        }
    }
}
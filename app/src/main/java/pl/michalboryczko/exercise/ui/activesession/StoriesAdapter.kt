package pl.michalboryczko.exercise.ui.activesession

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.story_item.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.api.Story


interface OnStoryClickListener{
    fun onStoryClicked(story: Story)
}


class StoriesAdapter(val items : List<Story>, val context: Context,
                     val listener: OnStoryClickListener) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private var selectedIndex = -1

    fun notifySelectedElement(storyId: String?){
        if(storyId != null){
            for(i in 0 until itemCount){
                if(items[i].storyId == storyId){
                    selectedIndex = i
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.story_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(item){
            holder.storyName.text = story
            holder.storyDescription.text = description
        }

        val drawable = if(selectedIndex == position) R.drawable.story_background_selected else R.drawable.story_background
        holder.wholeRow.background = ContextCompat.getDrawable(context, drawable)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val storyName = view.storyName
        val storyDescription = view.storyDescription
        val wholeRow = view.wholeRow

        init {
            view.wholeRow.setOnClickListener {
                val story = items[layoutPosition]
                //selectedIndex = layoutPosition
                listener.onStoryClicked(story)
                //notifyDataSetChanged()
            }
        }
    }
}
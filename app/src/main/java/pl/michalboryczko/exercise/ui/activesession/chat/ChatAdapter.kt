package pl.michalboryczko.exercise.ui.activesession.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_message.view.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.presentation.ChatMessage

val OWN_MESSAGE = 1
val USER_MESSAGE = 2

class ChatAdapter(val items : List<ChatMessage>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = items.size


    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if(item.isOwnMessage) OWN_MESSAGE else USER_MESSAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            OWN_MESSAGE -> OwnMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_message_own, parent, false))
            else -> MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_message, parent, false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (getItemViewType(position)){
            OWN_MESSAGE -> { (holder as OwnMessageViewHolder).messgaeText.text = item.message }
            else -> {
                val messageViewHolder = holder as MessageViewHolder
                with(item){
                    holder.messgaeText.text = message
                    holder.usernameTextView.text = user
                }
            }
        }
    }


    inner class OwnMessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val messgaeText = view.messgaeText
    }

    inner class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val messgaeText = view.messgaeText
        val usernameTextView = view.usernameTextView
    }
}
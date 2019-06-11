package pl.michalboryczko.exercise.ui.activesession.chat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_chat.*

import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.model.api.Session
import pl.michalboryczko.exercise.model.base.Status
import pl.michalboryczko.exercise.model.presentation.ChatMessage


class ChatFragment : BaseFragment<ChatViewModel>() {

    private var adapter: ChatAdapter? = null

    companion object {
        fun newInstance(session: Session): ChatFragment {
            val f = ChatFragment()
            val args = Bundle()
            args.putSerializable("sessionId", session)
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args = arguments
        val session = args?.getSerializable("sessionId") as Session
        viewModel.initialize(session)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }


    override fun onResume() {
        super.onResume()
        sendMessageImage.setOnClickListener {
            addMessage()
        }

        observeMessages()
    }

    private fun addMessage(){
        viewModel.addMessage(messageEditText.text.toString())
        messageEditText.setText("")
    }

    fun observeMessages(){
        viewModel.messages.observe(this, Observer {
            it?.let { r ->
                when(r.status){
                    Status.INITIAL -> {}
                    Status.LOADING -> {}
                    Status.ERROR -> {}
                    Status.ERROR_ID -> {}
                    Status.SUCCESS -> {
                        val messages = r.data
                        if(messages != null){
                            showMessages(messages)
                            scrollToLastItem()
                        }
                    }
                }
            }
        })
    }

    private fun scrollToLastItem(){
        val size = adapter?.items?.size
        val position = if(size != null) size - 1 else 0
        chatRecycler.scrollToPosition(position)

    }

    private fun showMessages(messages: List<ChatMessage>){
        if(messages != null){
            chatRecycler.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
            adapter = ChatAdapter(messages, this.requireContext())
            chatRecycler.adapter = adapter
            showViews(chatRecycler)
        }else{
            hideViews(chatRecycler)
        }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

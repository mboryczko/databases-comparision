package pl.michalboryczko.exercise.ui.session

import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import pl.michalboryczko.exercise.R
import android.app.Activity
import com.google.android.material.textfield.TextInputEditText


data class SessionData(
        val sessionId: String,
        val password: String
)

class SessionDialog(val context: Activity, val listener: OnSessionListener){

    lateinit var builder: MaterialDialog.Builder
    private lateinit var sessionIdEditText: TextInputEditText
    private lateinit var sessionPasswordEditText: TextInputEditText


    fun initialize(){
        val view = context.layoutInflater.inflate(R.layout.session_create_dialog, null)
        sessionIdEditText = view.findViewById(R.id.sessionIdEditText)
        sessionPasswordEditText = view.findViewById(R.id.sessionPasswordEditText)

        builder = MaterialDialog.Builder(context)
                .customView(view, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onNegative { dialog, _ -> dialog.dismiss() }
    }



    fun getSessionData() = SessionData(sessionIdEditText.text.toString(), sessionPasswordEditText.text.toString())


    fun showCreateSessionDialog(){
        initialize()
        builder.title(R.string.create_session)
                .onPositive { _, _ ->
                    listener.createSession()
                }
                .show()
    }

    fun showJoinSessionDialog(){
        initialize()
        builder.title(R.string.join_session)
                .onPositive { _, _ ->
                    listener.joinSession()
                }
                .show()
    }

}
package pl.michalboryczko.exercise.ui.session

import com.afollestad.materialdialogs.MaterialDialog
import pl.michalboryczko.exercise.R
import android.app.Activity
import android.view.View
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.textfield.TextInputEditText
import android.widget.ArrayAdapter




data class SessionData(
        val sessionId: String,
        val password: String
)


data class SessionCreateData(
        val sessionId: String,
        val password: String,
        val options: List<String>
)

class SessionDialog(val context: Activity, val listener: OnSessionListener){

    lateinit var builder: MaterialDialog.Builder
    private lateinit var sessionIdEditText: TextInputEditText
    private lateinit var sessionPasswordEditText: TextInputEditText
    private lateinit var estimationsSpinner: AppCompatSpinner


    private fun initialize(){
        val view = context.layoutInflater.inflate(R.layout.session_create_dialog, null)
        sessionIdEditText = view.findViewById(R.id.sessionIdEditText)
        sessionPasswordEditText = view.findViewById(R.id.sessionPasswordEditText)
        estimationsSpinner = view.findViewById(R.id.estimationsSpinner)
        estimationsSpinner.adapter = ArrayAdapter<String>(context, R.layout.simple_spinner_item, context.resources.getStringArray(R.array.estimation_options))

        builder = MaterialDialog.Builder(context)
                .customView(view, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onNegative { dialog, _ -> dialog.dismiss() }
    }

    private fun showEstimationOptions(){
        estimationsSpinner.visibility = View.VISIBLE
    }


    fun getSessionData() = SessionData(sessionIdEditText.text.toString(), sessionPasswordEditText.text.toString())
    fun getSessionCreateData() = SessionCreateData(sessionIdEditText.text.toString(), sessionPasswordEditText.text.toString(), estimationsSpinner.selectedItem.toString().split(",").map { it.trim() })


    fun showCreateSessionDialog(){
        initialize()
        showEstimationOptions()
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
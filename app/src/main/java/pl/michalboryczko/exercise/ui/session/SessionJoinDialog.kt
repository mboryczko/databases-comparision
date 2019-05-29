package pl.michalboryczko.exercise.ui.session

import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import pl.michalboryczko.exercise.R
import android.app.Activity
import com.google.android.material.textfield.TextInputEditText




class SessionJoinDialog(val context: Activity, listener: OnSessionListener){
    fun show(){

        val view = context.layoutInflater.inflate(R.layout.session_create_dialog, null)

        val sessionIdEditText = view.findViewById<TextInputEditText>(R.id.sessionIdEditText)
        val sessionPasswordEditText = view.findViewById<TextInputEditText>(R.id.sessionPasswordEditText)

        MaterialDialog.Builder(context)
                .title("")
                .customView(view, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive { dialog, which ->  Toast.makeText(context, "positive ${sessionIdEditText.text}", Toast.LENGTH_SHORT)}
                .onNegative { dialog, which ->  }
                .show()
    }
}
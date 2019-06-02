package pl.michalboryczko.exercise.ui.session

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_session.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseActivity
import pl.michalboryczko.exercise.model.base.Status


interface OnSessionListener{
    fun joinSession()
    fun createSession()
}

class SessionActivity : BaseActivity<SessionViewModel>(), OnSessionListener {

    private val sessionDialog = SessionDialog(this, this)

    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, SessionActivity::class.java)
    }

    private fun openSessionCreateDialog(){
        sessionDialog.showCreateSessionDialog()
    }

    private fun openSessionJoinDialog(){
        sessionDialog.showJoinSessionDialog()
    }

    override fun joinSession() {
        val data = sessionDialog.getSessionData()
        viewModel.joinSession(data.sessionId, data.password)
    }

    override fun createSession() {
        val data = sessionDialog.getSessionCreateData()
        viewModel.createSession(data.sessionId, data.password, data.options)
    }

    override fun defaultErrorHandling(res: Int) {
        showError()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        createSessionMenuButton.setOnClickListener { openSessionCreateDialog() }
        joinSessionMenuButton.setOnClickListener { openSessionJoinDialog() }
        logoutMenuButton.setOnClickListener { viewModel.logout() }
        mySessionsMenuButton.setOnClickListener { navigator.navigateToMySessionsActivity(this) }
        observeUserLoginStatus()

        viewModel.session.observe(this, Observer {
            it?.let { r ->
                when(r.status){
                    Status.INITIAL -> initial()
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {showError()}
                    Status.ERROR_ID -> {showError()}
                    Status.SUCCESS -> {
                        val session = r.data
                        if(session != null){
                            navigator.navigateToActiveSessionActivity(this, session)
                        }
                    }
                }
            }
        })
    }

    fun initial(){
        enableViews(createSessionMenuButton, joinSessionMenuButton)
        hideViews(progressBar)
    }

    fun showLoading(){
       // disableViews(createSessionMenuButton, joinSessionMenuButton)
        showViews(progressBar)
    }

    fun showError(){
        hideViews(progressBar)
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

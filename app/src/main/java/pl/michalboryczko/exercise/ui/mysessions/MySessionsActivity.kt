package pl.michalboryczko.exercise.ui.mysessions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_my_sessions.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseActivity
import pl.michalboryczko.exercise.model.api.Session
import pl.michalboryczko.exercise.model.base.Status


class MySessionsActivity : BaseActivity<MySessionsViewModel>(), OnSessionClickedListener {

    companion object {
        fun prepareIntent(activity: Activity): Intent{
            return Intent(activity, MySessionsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_sessions)

        viewModel.sessions.observe(this, Observer {
            it?.let { r->
                when(r.status){
                    Status.INITIAL -> {}
                    Status.LOADING -> {}
                    Status.ERROR -> {}
                    Status.ERROR_ID -> {}
                    Status.SUCCESS -> {
                        val sessions = r.data
                        if(sessions != null){
                            showSessions(sessions)
                        }
                    }
                }
            }
        })
    }

    override fun defaultErrorHandling(res: Int) {
        showToast(this, res)
    }

    fun showSessions(sessions: List<Session>){
        sessionsRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        sessionsRecycler.adapter = MySessionsAdapter(sessions, this, this)
    }

    override fun sessionClicked(session: Session) {
        navigator.navigateToActiveSessionActivity(this, session)
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

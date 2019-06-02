package pl.michalboryczko.exercise.ui.activesession

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_active_session.*
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseActivity
import pl.michalboryczko.exercise.model.api.Session
import pl.michalboryczko.exercise.ui.activesession.chat.ChatFragment
import pl.michalboryczko.exercise.ui.activesession.chat.SessionFragment
import pl.michalboryczko.exercise.utils.Constants



class ActiveSessionActivity : BaseActivity<ActiveSessionViewModel>() {

    companion object {
        fun prepareIntent(activity: Activity, session: Session): Intent{
            val intent = Intent(activity, ActiveSessionActivity::class.java)
            intent.putExtra(Constants.SESSION_BUNDLE, session)
            return intent
        }
    }

    override fun defaultErrorHandling(res: Int) {
        showToast(this, res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_session)

        if(intent.hasExtra(Constants.SESSION_BUNDLE)){
            val session = intent.getSerializableExtra(Constants.SESSION_BUNDLE) as Session
            //viewModel.initialize(session)

            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(SessionFragment.newInstance(session), "STORY")
            adapter.addFragment(ChatFragment.newInstance(session), "CHAT")
            viewpager.adapter = adapter
            tabs.setupWithViewPager(viewpager)
        }


        observeUserLoginStatus()
    }



    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

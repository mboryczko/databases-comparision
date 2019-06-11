package pl.michalboryczko.exercise.ui.activesession.session


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_session.*

import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseFragment
import pl.michalboryczko.exercise.model.ActiveSession
import pl.michalboryczko.exercise.model.api.Estimation
import pl.michalboryczko.exercise.model.api.Session
import pl.michalboryczko.exercise.model.api.Story
import pl.michalboryczko.exercise.model.base.Status

class SessionFragment : BaseFragment<ActiveSessionViewModel>(), OnStoryClickListener, OnEstimationChoosenListener {

    companion object {
        fun newInstance(session: Session): SessionFragment {
            val f = SessionFragment()
            val args = Bundle()
            args.putSerializable("sessionId", session)
            f.arguments = args
            return f
        }
    }

    private var storiesAdapter: StoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args = arguments
        val session = args?.getSerializable("sessionId") as Session
        viewModel.initialize(session)
        return inflater.inflate(R.layout.fragment_session, container, false)
    }

    override fun onStart() {
        super.onStart()
        initClickListeners()
        observeStory()
        observeStories()
        observeSession()
        initEstimationsToChoseRecycler(listOf("1", "2", "4", "8", "16", "32"))
    }

    fun initEstimationsToChoseRecycler(estimations: List<String>){
        estimationsToChoseRecycler.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        estimationsToChoseRecycler.adapter = EstimationsToChooseAdapter(estimations, this.requireContext(), this)

    }

    override fun estimationChoosen(estimation: String) {
        viewModel.saveEstimationClicked(estimation)
    }

    fun initClickListeners(){
        createStoryButton.setOnClickListener {
            viewModel.createStory(createStoryName.text.toString(), createStoryDescription.text.toString())
            createStoryName.setText("")
            createStoryDescription.setText("")
        }

        saveEstimationButton.setOnClickListener {
            viewModel.saveEstimationClicked(pointsEstimation.text.toString())
            pointsEstimation.setText("")
        }


    }

    override fun onStoryClicked(story: Story) {
        viewModel.storyClicked(story)
    }



    fun observeStory(){
        viewModel.story.observe(this, Observer {
            it?.let { r ->
                when(r.status){
                    Status.INITIAL -> {}
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {}
                    Status.ERROR_ID -> {}
                    Status.SUCCESS -> {
                        val activeStory = r.data
                        if(activeStory != null){
                            showStory(activeStory)
                        }
                    }
                }
            }
        })
    }

    fun observeStories(){
        viewModel.stories.observe(this, Observer {
            it?.let {r->
                when(r.status){
                    Status.INITIAL -> {}
                    Status.LOADING -> showStoriesLoading()
                    Status.ERROR -> {}
                    Status.ERROR_ID -> {}
                    Status.SUCCESS -> {
                        val stories = r.data
                        if(stories != null){
                            showStories(stories)
                        }

                    }
                }
            }
        })
    }

    fun observeSession(){
        viewModel.session.observe(this, Observer {
            it?.let { r ->
                when(r.status){
                    Status.INITIAL -> {}
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {}
                    Status.ERROR_ID -> {}
                    Status.SUCCESS -> {
                        val activeSession = r.data
                        if(activeSession != null){
                            showSession(activeSession)
                        }

                    }
                }
            }
        })
    }

    fun showStoriesLoading(){
        hideViews(storiesRecycler)
    }

    fun showLoading(){
        //hideViews(activeSession, activeStory, activeStoryDescription)
    }

    private fun showStories(stories: List<Story>){
        if(stories != null){
            storiesRecycler.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
            storiesAdapter = StoriesAdapter(stories, this.requireContext(), this)
            storiesRecycler.adapter = storiesAdapter
            showViews(storiesRecycler)
        }else{
            hideViews(storiesRecycler)
        }
    }

    private fun showSession(session: ActiveSession){
        //activeSession.text = session.session.name
        initEstimationsToChoseRecycler(session.session.options ?: listOf())
        if(!session.isMaster){
            hideViews(createStoryLayout)
        }
    }



    private fun showStory(story: Story){
        storiesAdapter?.notifySelectedElement(story.storyId)
        val estimations = story.estimations
        if(estimations != null){
            estimationsRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
            estimationsRecyclerView.adapter = EstimationsAdapter(convert(estimations.toList()), this.requireContext())
            showViews(estimationsRecyclerView)
        }else{
            hideViews(estimationsRecyclerView)
        }

    }

    private fun convert(list: List<Pair<String, Estimation>>): List<Estimation>{
        val output = mutableListOf<Estimation>()
        list.forEach {
            output.add(it.second)
        }

        return output
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}

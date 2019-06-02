package pl.michalboryczko.exercise.ui.mysessions


import androidx.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.ActiveSession
import pl.michalboryczko.exercise.model.api.Session
import pl.michalboryczko.exercise.model.api.Story
import pl.michalboryczko.exercise.model.base.Event
import pl.michalboryczko.exercise.model.base.Resource
import pl.michalboryczko.exercise.model.exceptions.NoInternetException
import pl.michalboryczko.exercise.model.exceptions.ApiException
import pl.michalboryczko.exercise.model.presentation.ChatMessage
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named


class MySessionsViewModel
@Inject constructor(
        private val repository: Repository,
        private val userRepository: UserRepository,
        private val checker: InternetConnectivityChecker,
        @Named("computationScheduler") private val computationScheduler: Scheduler,
        @Named("mainScheduler") private val mainScheduler: Scheduler
) : BaseViewModel(checker, userRepository) {

    val sessions: MutableLiveData<Resource<List<Session>>> = MutableLiveData()

    init {
        getSessions()
    }

    fun getSessions(){
        disposables.add(
                repository
                        .getUserSessions()
                        .subscribeOn(computationScheduler)
                        .observeOn(mainScheduler)
                        .subscribe(
                                {
                                    sessions.value = Resource.success(it)
                                },
                                {defaultErrorHandling(it)}
                        )
        )
    }

}
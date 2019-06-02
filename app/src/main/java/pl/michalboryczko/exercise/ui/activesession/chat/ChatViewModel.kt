package pl.michalboryczko.exercise.ui.activesession.chat


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


class ChatViewModel
@Inject constructor(
        private val repository: Repository,
        private val userRepository: UserRepository,
        private val checker: InternetConnectivityChecker,
        @Named("computationScheduler") private val computationScheduler: Scheduler,
        @Named("mainScheduler") private val mainScheduler: Scheduler
) : BaseViewModel(checker, userRepository) {

    val session: MutableLiveData<Resource<ActiveSession>> = MutableLiveData()
    val story: MutableLiveData<Resource<Story>> = MutableLiveData()
    val stories: MutableLiveData<Resource<List<Story>>> = MutableLiveData()
    val messages: MutableLiveData<Resource<List<ChatMessage>>> = MutableLiveData()

    fun initialize(sessionProvided: Session) {
        disposables.add(
                userRepository
                        .getCurrentUserId()
                        .subscribe(
                                { uid ->
                                    val activeSession = ActiveSession(sessionProvided.managerId == uid, sessionProvided)
                                    session.value = Resource.success(activeSession)
                                    observeMessages(sessionProvided.sessionId)
                                },
                                {defaultErrorHandling(it)}
                        )
        )
    }



    fun addMessage(message: String){
        val sessionId = session.value?.data?.session?.sessionId
        if(sessionId != null){
            disposables.add(
                    repository
                            .addMessage(sessionId, message)
                            .subscribeOn(computationScheduler)
                            .observeOn(mainScheduler)
                            .subscribe(
                                    {
                                        toastInfo.value = Event("message added")
                                    },
                                    {defaultErrorHandling(it)}
                            )
            )
        }
    }

    fun observeMessages(sessionId: String){
        disposables.add(
                repository
                        .observeMessages(sessionId)
                        .subscribeOn(computationScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { messages.value = Resource.loading() }
                        .subscribe(
                                {
                                    messages.value = Resource.success(it)
                                },
                                {
                                    defaultErrorHandling(it)
                                }
                        )
        )
    }


}
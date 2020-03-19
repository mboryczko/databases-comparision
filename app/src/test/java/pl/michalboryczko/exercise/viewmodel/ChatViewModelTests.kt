package pl.michalboryczko.exercise.viewmodel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


import org.mockito.Mockito.*
import pl.michalboryczko.exercise.BaseTest
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.base.Event
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository


class ChatViewModelTests: BaseTest() {

    private val userRepository = mock(UserRepository::class.java)
    private val repo = mock(Repository::class.java)
    //private val viewmodel by lazy { ChatViewModel(repo, userRepository, internetChecker, Schedulers.trampoline(), Schedulers.trampoline()) }



    @Before
    override fun setUp(){
        whenever(userRepository.isUserLoggedIn()).thenReturn(Flowable.just(true))
        whenever(userRepository.getCurrentUserId()).thenReturn(Single.just(userMock.id))
        whenever(internetChecker.isInternetAvailableObservable()).thenReturn(Observable.just(true))
        //whenever(repo.observeMessages(sessionMock.sessionId)).thenReturn(Flowable.just(listOf(chat1Mock)))
        whenever(userRepository.getCurrentUserId()).thenReturn(Single.just(userMock.id))

    }

    @Test
    fun addMessageTest(){
        /*whenever(repo.addMessage(sessionMock.sessionId, chat1Mock.message)).thenReturn(Single.just(true))
        viewmodel.initialize(sessionMock)
        viewmodel.addMessage(chat1Mock.message)
        assertEquals(Event(R.string.message_added).peekContent(), viewmodel.toastInfoResource.value?.peekContent())*/
    }





}

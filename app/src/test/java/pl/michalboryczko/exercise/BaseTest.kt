package pl.michalboryczko.exercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import pl.michalboryczko.exercise.helper.RandomInputs
import pl.michalboryczko.exercise.model.api.*
import pl.michalboryczko.exercise.model.api.call.LoginCall
import pl.michalboryczko.exercise.model.api.call.UserCall
import pl.michalboryczko.exercise.model.presentation.User
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import java.util.*

open class BaseTest {
    protected val internetChecker = Mockito.mock(InternetConnectivityChecker::class.java)

    @Before
    open fun setUp(){
        whenever(internetChecker.isInternetAvailableObservable()).thenReturn(Observable.just(true))
        whenever(internetChecker.isInternetAvailable()).thenReturn(true)
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val random = RandomInputs()
    protected val userMock = User("userId", "email",  "username")
    protected val sessionMock = Session("sessionId", "userId", "sessionName", "sessionPassword", "storyId", null)

    //protected val chat1Mock = ChatMessage(userMock.username, "message", Date().toString(), userMock.id, sessionMock.sessionId,  false)
    protected val estimation1Mock = Estimation("storyId", "2", "michal", "userId")

    protected val storyMock = Story("storyId", "sessionId", "logging in", "logging description", hashMapOf("userId" to estimation1Mock))



    inline fun <reified T> mock() = Mockito.mock(T::class.java)
    inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> =
            Mockito.`when`(methodCall)

    protected fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    fun generateValidUser(): UserCall {
        val email = random.generateRandomEmail()
        val username = random.generateRandomString(7)
        val password = random.generateStrongPassword()

        return UserCall(email, password, username)
    }

    fun generateValidLoginCall(): LoginCall {
        val email = random.generateRandomEmail()
        val password = random.generateStrongPassword()
        return LoginCall(email, password)
    }
}
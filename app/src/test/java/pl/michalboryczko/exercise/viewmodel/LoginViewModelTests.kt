package pl.michalboryczko.exercise.viewmodel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert
import org.junit.Before
import org.junit.Test


import org.mockito.Mockito.*
import pl.michalboryczko.exercise.BaseTest
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.model.api.call.LoginCall
import pl.michalboryczko.exercise.model.base.Resource
import pl.michalboryczko.exercise.model.exceptions.NoInternetException
import pl.michalboryczko.exercise.source.repository.UserRepository


class LoginViewModelTests: BaseTest() {

    private val repo = mock(UserRepository::class.java)
    //private val viewmodel by lazy { MainViewModel(repo, internetChecker, Schedulers.trampoline(), Schedulers.trampoline()) }



    @Before
    override fun setUp(){
        whenever(repo.isUserLoggedIn()).thenReturn(Flowable.just(true))
        whenever(internetChecker.isInternetAvailableObservable()).thenReturn(Observable.just(true))
    }

    /*

    @Test
    fun loginPositiveTest(){
        whenever(repo.logIn(anyObject())).thenReturn(Single.just(true))
        viewmodel.loginClicked(generateValidLoginCall())
        Assert.assertEquals(Resource.success(R.string.login_successful), viewmodel.status.value)
    }

    @Test
    fun loginWrongUserTest(){
        whenever(repo.logIn(anyObject())).thenReturn(Single.just(true))
        viewmodel.loginClicked(LoginCall("", random.generateStrongPassword()))
        Assert.assertEquals(Resource.error<Int>(R.string.login_empty), viewmodel.status.value)
    }

    @Test
    fun loginNoInternetTest(){
        whenever(repo.logIn(anyObject())).thenReturn(Single.error(NoInternetException()))
        viewmodel.loginClicked(generateValidLoginCall())
        Assert.assertEquals(Resource.error<NoInternetException>(R.string.no_internet), viewmodel.status.value)
    }

*/

}

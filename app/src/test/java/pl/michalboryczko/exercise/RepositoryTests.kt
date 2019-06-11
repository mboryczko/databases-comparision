package pl.michalboryczko.exercise

import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.firebase.FirestoreApiService
import pl.michalboryczko.exercise.source.repository.RepositoryImpl
import pl.michalboryczko.exercise.source.repository.UserRepository

class RepositoryTests: BaseTest() {


    private val firestoreApiService = Mockito.mock(FirestoreApiService::class.java)
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val repo by lazy { RepositoryImpl(userRepository, firestoreApiService, internetChecker) }

    @Before
    override fun setUp(){
        whenever(firestoreApiService.loadStory(sessionMock.sessionId, sessionMock.currentStory!!)).thenReturn(Flowable.just(storyMock))
        whenever(firestoreApiService.observeSessionById(sessionMock.sessionId)).thenReturn(Flowable.just(sessionMock))
        whenever(internetChecker.isInternetAvailable()).thenReturn(true)
    }


    @Test
    fun observeCurrentStoryTest(){
        repo.observeCurrentStory(sessionMock.sessionId)
                .test()
                .assertValue { it.storyId == storyMock.storyId }
                .assertValue { it.story == storyMock.story }
    }

    @Test
    fun internetConnectivityChecker(){
        Assert.assertEquals(internetChecker.isInternetAvailable(), true)
    }


}
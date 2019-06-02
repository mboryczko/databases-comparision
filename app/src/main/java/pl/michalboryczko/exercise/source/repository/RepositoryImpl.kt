package pl.michalboryczko.exercise.source.repository

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow
import pl.michalboryczko.exercise.model.api.*
import pl.michalboryczko.exercise.model.exceptions.NotFoundException
import pl.michalboryczko.exercise.model.presentation.ChatMessage
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.firebase.FirebaseApiService
import pl.michalboryczko.exercise.source.api.firebase.FirestoreApiService
import timber.log.Timber

class RepositoryImpl(
        private val userRepository: UserRepository,
        private val firestoreApiService: FirestoreApiService,
        private val checker: InternetConnectivityChecker
) :Repository, NetworkRepository(checker) {

    override fun createSession(name: String, password: String, estimationOptions: List<String>): Single<Session> {
        return userRepository
                .getCurrentUserId()
                .flatMap { managerId ->
                    firestoreApiService.createSession(managerId, name, password, estimationOptions)
                }.compose(handleNetworkConnection())
                .compose(handleExceptions())
    }

    override fun getUserSessions(): Single<List<Session>> {
        return userRepository
                .getCurrentUserId()
                .flatMap {uid->
                    firestoreApiService
                        .getUserSessions(uid) }
                .compose(handleExceptions())
                .compose(handleNetworkConnection())
    }

    override fun observeMessages(sessionId: String): Flowable<List<ChatMessage>> {
        return userRepository
                    .getCurrentUserId()
                    .flatMapObservable { uid ->
                        firestoreApiService
                                .observeChatMessages(sessionId)
                                .map {messages ->
                                    val chatMessages = mutableListOf<ChatMessage>()
                                    messages.forEach {
                                        chatMessages.add(ChatMessage(it.user, it.message, it.time,
                                                it.uid, it.sessionId, it.uid == uid))
                                    }
                                    return@map chatMessages.toList()
                                }
                    }.toFlowable(BackpressureStrategy.BUFFER)

    }

    override fun updateCurrentStoryUnderSession(story:Story): Single<Boolean>{
        return firestoreApiService
                .updateSession(story.sessionId, story.storyId)
    }

    override fun addMessage(sessionId: String, message: String): Single<Boolean> {
        return userRepository
                .getCurrentUser()
                .flatMap { user ->
                    firestoreApiService.addMessage(sessionId, user, message)
                }
                .compose(handleNetworkConnection())
                .compose(handleExceptions())
    }

    /*
            CREATES STORY FOR SPECIFIC SESSION
            AND UPDATES CURRENT_STORY UNDER SPECIFIC SESSION
         */
    override fun createStory(sessionId: String, story:String, description: String): Single<Story> {
        return firestoreApiService
                .saveStory(sessionId, story, description)
                .flatMap {story ->
                    firestoreApiService.updateSession(sessionId, story.storyId)
                            .map { story }
                }
                .compose(handleNetworkConnection())
                .compose(handleExceptions())
    }

    override fun joinSession(sessionId: String, password: String): Single<Session> {
        return firestoreApiService
                .joinSession(sessionId, password)
                .compose(handleNetworkConnection())
                .compose(handleExceptions())
    }

    override fun saveEstimation(storyId: String, points: String): Single<Boolean> {
        return userRepository
                .getCurrentUser()
                .flatMap { user ->
                    val estimation = Estimation(storyId, points, user.username, user.id)
                    firestoreApiService.setEstimation(estimation)
                }
    }

    override fun observeCurrentStory(sessionId: String): Flowable<Story> {
        return firestoreApiService
                .observeSessionById(sessionId)
                .flatMap { session ->
                    Timber.d("session changed in flatmap %s", session.toString())
                    if(session.currentStory != null){
                        firestoreApiService.loadStory(sessionId, session.currentStory)
                    }else{
                        Flowable.empty()
                    }
                }
                .compose(handleNetworkConnectionFlowable())
                .compose(handleExceptionsFlowable())
    }

    override fun observeStories(sessionId: String): Flowable<List<Story>> {

        return firestoreApiService
                .loadStories(sessionId)
                .compose(handleNetworkConnectionFlowable())
                .compose(handleExceptionsFlowable())
    }

}
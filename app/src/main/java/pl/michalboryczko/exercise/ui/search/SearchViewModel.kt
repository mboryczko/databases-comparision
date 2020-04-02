package pl.michalboryczko.exercise.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.base.Resource
import pl.michalboryczko.exercise.model.database.realm.DoctorRealm
import pl.michalboryczko.exercise.model.database.realm.Specialization
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.realm.convertToTranslateRealmList
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.database.room.convertToTranslateRoomList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.impl.RealmDatabaseImpl
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import pl.michalboryczko.exercise.utils.ExecutionTimer
import pl.michalboryczko.exercise.utils.WordParser
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

class SearchViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val repository: Repository,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val words: MutableLiveData<List<Translate>> = MutableLiveData()

    init {
        searchWords("")
    }

    fun onTextChanged(word: String){
        searchWords(word)
    }

    fun searchWords(text: String){
        val timer = ExecutionTimer()
        disposables += userRepository.searchWordsToLearn(text)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("searchWords get")
                            words.value = it
                            Timber.d("searchWords count: ${it.count()}")
                            if(it.count() >= 1)
                                Timber.d("searchWords first two : ${it[0]} ")
                        },
                        {
                            Timber.d("searchWords error mes: ${it.message}")
                        }
                )
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

}
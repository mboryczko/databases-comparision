package pl.michalboryczko.exercise.ui.learnwords

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.Single
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.base.Resource
import pl.michalboryczko.exercise.model.database.realm.convertToTranslateRealmList
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.database.room.convertToTranslateRoomList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import pl.michalboryczko.exercise.utils.ExecutionTimer
import pl.michalboryczko.exercise.utils.WordParser
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class WordsLearningViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val repository: Repository,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val status: MutableLiveData<Resource<Int>> = MutableLiveData()
    lateinit var words: List<Translate>
    val currentTranslation: MutableLiveData<Translate> = MutableLiveData()

    init {
        words =  WordParser.parseWords("dictionary.txt")
    }

    fun checkCurrentTranslation(){

    }



    fun getWordsFromDb(){
        val timer = ExecutionTimer()
        disposables +=
                Single.just(true)
                .flatMap { userRepository.getAllWords() }
                .subscribeOn(computationScheduler)
                .observeOn(computationScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("VIEWMODEL get")
                            Timber.d("getWordsFromDb count: ${it.count()}")
                        },
                        {
                            Timber.d("getWordsFromDb error mes: ${it.message}")
                            Timber.d("getWordsFromDb error loc: ${it.localizedMessage}")
                        }
                )
    }

    fun saveWordsToDb(){
        val timer = ExecutionTimer()
        disposables += userRepository
                .saveAllWords(words)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("VIEWMODEL save")
                            Timber.d("saveAllWords success")
                        },
                        {
                            Timber.d("saveAllWords error mes: ${it.message}")
                            Timber.d("saveAllWords error loc: ${it.localizedMessage}")
                        }
                )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

}
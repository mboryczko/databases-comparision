package pl.michalboryczko.exercise.ui.learnwords

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmList
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.base.Event
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

class WordsLearningViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val repository: Repository,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val currentTranslation: MutableLiveData<Translate> = MutableLiveData()
    val correctAnswear: MutableLiveData<Boolean> = MutableLiveData()
    val wrongAnswear: MutableLiveData<Boolean> = MutableLiveData()
    val showNoLearningWordsDialog: MutableLiveData<Event<Boolean>> = MutableLiveData()

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        getNextWord()
    }

    fun onCheckClicked(word: String){
        if(currentTranslation.value!!.spanish == word){
            correctAnswear.value = true
            markWordAnsweredRight()
        }else{
            wrongAnswear.value = true
        }
    }

    fun getNextWord(){
        disposables +=  userRepository.searchWordsToLearn()
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        {
                            if(it.isNotEmpty()){
                                currentTranslation.value = it.random()
                                it.forEach {
                                    Timber.d("getNextWord word: ${it}")
                                }
                            }

                            Timber.d("getNextWord collection empty")

                        },
                        {
                            if(it is NoSuchElementException){
                                showNoLearningWordsDialog.value = Event(true)
                            }else{

                            }
                            Timber.d("getNextWord error mes: ${it.message}")
                            Timber.d("getNextWord error loc: ${it.localizedMessage}")
                        }
                )
    }

    fun markWordAnsweredRight(){
        val word = currentTranslation.value
        word?.let {
            disposables += userRepository.updateAsAnsweredRight(word)
                    .subscribeOn(computationScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(
                            {
                                Timber.d("updateAsAnsweredRight success")
                            },
                            {
                                Timber.d("updateAsAnsweredRight error mes: ${it.message}")
                            }
                    )
        }
    }


    /*fun getWordsFromDb(){
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
                .saveAllWords(wordsToLearn)
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
    }*/


}
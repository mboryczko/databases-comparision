package pl.michalboryczko.exercise.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import pl.michalboryczko.exercise.utils.ExecutionTimer
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SearchViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val repository: Repository,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val wordsToLearn: MutableLiveData<List<Translate>> = MutableLiveData()
    val wordsLearned: MutableLiveData<List<Translate>> = MutableLiveData()

    init {
        searchWordsToLearn("")
        searchLearnedWords("")
    }

    fun onTextChanged(word: String, toLearn: Boolean){
        if(toLearn){
            searchWordsToLearn(word)
        }else{
            searchLearnedWords(word)
        }
    }

    fun searchLearnedWords(text: String){
        val timer = ExecutionTimer()
        disposables += userRepository.searchLearnedWords(text)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("searchLearnedWords get")
                            wordsLearned.value = it
                            Timber.d("searchLearnedWords count: ${it.count()}")
                            if(it.count() >= 1)
                                Timber.d("searchLearnedWords first two : ${it[0]} ")
                        },
                        {
                            Timber.d("searchLearnedWords error mes: ${it.message}")
                        }
                )
    }


    fun searchWordsToLearn(text: String){
        val timer = ExecutionTimer()
        disposables += userRepository.searchWordsToLearn(text)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("searchWordsToLearn get")
                            wordsToLearn.value = it
                            Timber.d("searchWordsToLearn count: ${it.count()}")
                            if(it.count() >= 1)
                                Timber.d("searchWordsToLearn first two : ${it[0]} ")
                        },
                        {
                            Timber.d("searchWordsToLearn error mes: ${it.message}")
                        }
                )
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

}
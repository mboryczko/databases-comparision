package pl.michalboryczko.exercise.ui.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import pl.michalboryczko.exercise.utils.ExecutionTimer
import pl.michalboryczko.exercise.utils.WordParser.Companion.parseSentences
import pl.michalboryczko.exercise.utils.WordParser.Companion.parseWords
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class MainViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val checker: InternetConnectivityChecker,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val words: MutableLiveData<List<Translate>> = MutableLiveData()
    val sentences: MutableLiveData<List<Translate>> = MutableLiveData()

    init {
        //wordsToLearn.value = parseWords("dictionary.txt")
        //sentences.value = parseSentences("sentences.txt")

        val timer = ExecutionTimer()
        disposables +=
                userRepository.getAllWords()
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            timer.stopTimer("getAllWords MainViewModel")
                            words.value = it
                            Timber.d("getAllWords MainViewModel count: ${it.count()}")
                            if(it.count() >= 1)
                                Timber.d("getAllWords MainViewModel first two : ${it[0]} ")
                        },
                        {
                            Timber.d("getAllWords MainViewModel error mes: ${it.message}")
                        }
                )
    }

}
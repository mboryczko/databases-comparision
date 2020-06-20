package pl.michalboryczko.exercise.ui.settings

import io.reactivex.Scheduler
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.source.repository.UserRepositoryImpl
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class SettingsViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val checker: InternetConnectivityChecker,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    fun setDatabase(database: UserRepositoryImpl.DATABASE_IMPL) {
        userRepository.changeDatabase(database)
    }

    fun deleteAllWords(){
        disposables += userRepository.deleteAllWords()
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        {
                            Timber.d("deleteAllWords() success")
                        },
                        {
                            Timber.d("deleteAllWords() e: $it")
                        }
                )
    }


    fun parseFirstNWords(n: Int){
        disposables += userRepository.parseFirstNWords(n)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        {
                            Timber.d("parseFirstNWords() success")
                        },
                        {
                            Timber.d("parseFirstNWords() e: $it")
                        }
                )
    }

}
package pl.michalboryczko.exercise.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.app.BaseViewModel
import pl.michalboryczko.exercise.model.base.Event
import pl.michalboryczko.exercise.model.database.greendao.TranslateGreen
import pl.michalboryczko.exercise.model.database.greendao.TranslateGreenDao
import pl.michalboryczko.exercise.model.presentation.MarkTranslate
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.model.presentation.convertToMarkTranslate
import pl.michalboryczko.exercise.model.presentation.convertToTranslate
import pl.michalboryczko.exercise.source.repository.Repository
import pl.michalboryczko.exercise.source.repository.UserRepository
import pl.michalboryczko.exercise.utils.Constants.Companion.COMPUTATION_SCHEDULER
import pl.michalboryczko.exercise.utils.Constants.Companion.MAIN_SCHEDULER
import pl.michalboryczko.exercise.utils.ExecutionTimer
import pl.michalboryczko.exercise.ui.search.SearchFragment.SEARCH_PAGE.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SearchViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val repository: Repository,
        private val greenDao: TranslateGreenDao,
        @Named(COMPUTATION_SCHEDULER) private val computationScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) private val mainScheduler: Scheduler
) : BaseViewModel(userRepository) {

    val wordsToLearn: MutableLiveData<Event<List<Translate>>> = MutableLiveData()
    val wordsLearned: MutableLiveData<Event<List<Translate>>> = MutableLiveData()
    val allWords: MutableLiveData<Event<List<MarkTranslate>>> = MutableLiveData()
    private var wordToSearch = ""

    init {
        initialize()
    }

    fun initialize(){
        searchWordsToLearn(wordToSearch)
        searchLearnedWords(wordToSearch)
        searchAllWords(wordToSearch)
    }


    fun onTextChanged(word: String, screen: SearchFragment.SEARCH_PAGE){
        wordToSearch = word
        when(screen){
            TO_LEARN -> searchWordsToLearn(word)
            LEARNED -> searchLearnedWords(word)
            ALL ->  searchAllWords(word)
        }
    }

    fun searchAllWords(text: String){
        val timer = ExecutionTimer()
        disposables += userRepository.searchWords(text)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            //timer.stopTimer("searchAllWords get")
                            val list = it.map { it.convertToMarkTranslate() }
                            allWords.value = Event(list)
                            Timber.d("searchAllWords count: ${it.count()}")
                        },
                        {
                            Timber.d("searchAllWords error mes: ${it.message}")
                        }
                )

        /*if(text == "a"){
            greenDao.insert(TranslateGreen(1L, "lool", "puta", 0, 0, false))
            Timber.d("searchAllWords greenDao inserted")
        }else{
            val qb = greenDao
                    .queryBuilder()
                    //.where(TranslateGreenDao.Properties.English.eq("what"))
                    //.where(TranslateGreenDao.Properties.English.like(text))
                    //.whereOr(TranslateGreenDao.Properties.English.like(text), TranslateGreenDao.Properties.English.like(text))
                    .build()
                    .list()

            Timber.d("searchAllWords greenDao count: ${qb.count()}")
        }*/


    }

    fun deleteWords(words: List<MarkTranslate>){
        val timer = ExecutionTimer()
        val list = words.map { it.convertToTranslate() }
        disposables += userRepository.deleteWords(list)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            toastInfoResource.value = Event(R.string.delete_word)
                            timer.stopTimer("deleteWords")
                            initialize()
                        },
                        {
                            Timber.d("addToLearningList error mes: ${it.message}")
                        }
                )
    }

    fun addToLearningList(words: List<MarkTranslate>){
        val timer = ExecutionTimer()
        val list = words.map { it.convertToTranslate() }
        disposables += userRepository.updateAsLearning(list)
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { timer.startTimer() }
                .subscribe(
                        {
                            toastInfoResource.value = Event(R.string.added_to_learning_list)
                            timer.stopTimer("addToLearningList")
                            //initialize()
                        },
                        {
                            Timber.d("addToLearningList error mes: ${it.message}")
                        }
                )
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
                            wordsLearned.value = Event(it)
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
                            wordsToLearn.value = Event(it)
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
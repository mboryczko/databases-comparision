package pl.michalboryczko.exercise.source.databases

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.utils.ExecutionTimer

abstract class DatabaseOperations {
    protected val timer = ExecutionTimer()
    abstract fun updateWords(words: List<Translate>): Completable
    abstract fun deleteWords(words: List<Translate>): Completable
    abstract fun deleteAll(): Completable
    abstract fun saveAllWords(words: List<Translate>): Completable
    abstract fun fetchAllWords(): Single<List<Translate>>
    abstract fun searchWordsToLearn(text: String): Single<List<Translate>>
    abstract fun searchLearnedWords(text: String): Single<List<Translate>>
    abstract fun searchWordsByPhrase(text: String): Single<List<Translate>>
}




package pl.michalboryczko.exercise.source.databases

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.utils.ExecutionTimer

abstract class DatabaseOperations {
    protected val timer = ExecutionTimer()

    abstract fun fetchAllWords(): Single<List<Translate>>
    abstract fun searchWords(text: String): Single<List<Translate>>
    abstract fun saveAllWords(words: List<Translate>): Completable



}
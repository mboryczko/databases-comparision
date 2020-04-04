package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.presentation.Translate

interface Repository {

}

interface UserRepository{
    fun getAllWords(): Single<List<Translate>>
    fun searchWords(text: String): Single<List<Translate>>
    fun searchWordsToLearn(text: String = ""): Flowable<List<Translate>>
    fun searchLearnedWords(text: String = ""): Flowable<List<Translate>>

    fun saveAllWords(words: List<Translate>): Completable
    fun updateAsLearning(words: List<Translate>): Completable
    fun updateAsAnsweredRight(word: Translate): Completable
    fun deleteWords(words: List<Translate>): Completable

}
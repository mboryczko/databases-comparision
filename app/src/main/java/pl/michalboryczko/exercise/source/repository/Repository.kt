package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.presentation.Translate

interface Repository {

}

interface UserRepository{
    fun getAllWords(): Single<List<Translate>>
    fun saveAllWords(words: List<Translate>): Completable

}
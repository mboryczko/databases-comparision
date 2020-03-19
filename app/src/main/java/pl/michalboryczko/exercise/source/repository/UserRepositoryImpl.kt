package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.database.room.convertToTranslate
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.databases.DatabaseOperations

class UserRepositoryImpl (
        private var room: DatabaseOperations,
        private var realm: DatabaseOperations,

        private val checker: InternetConnectivityChecker
) :UserRepository, NetworkRepository(checker) {

    override fun getAllWords(): Single<List<Translate>> {
        return room.fetchAllWords()
                .flatMap { realm.fetchAllWords() }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        return room.saveAllWords(words)
                .andThen(realm.saveAllWords(words))
    }

}
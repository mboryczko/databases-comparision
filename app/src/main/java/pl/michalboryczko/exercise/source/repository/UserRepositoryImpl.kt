package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import pl.michalboryczko.exercise.source.databases.impl.*

class UserRepositoryImpl (
        private var room: RoomDatabaseImpl,
        private var realm: RealmDatabaseImpl,
        private var couchbase: CouchbaseDatabaseImpl,
        private var ormLite: OrmLiteDatabaseImpl,
        private var objectBox: ObjectBoxDatabaseImpl,
        private val checker: InternetConnectivityChecker
) :UserRepository, NetworkRepository(checker) {

    override fun getAllWords(): Single<List<Translate>> {
        return Single.defer { objectBox.fetchAllWords() }
                .flatMap { room.fetchAllWords() }
                //.flatMap { ormLite.fetchAllWords() }
                .flatMap { realm.fetchAllWords() }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        /*return room.saveAllWords(words)
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(realm.saveAllWords(words))
                .andThen(ormLite.saveAllWords(words))*/

        return Completable.defer { objectBox.saveAllWords(words) }
                .andThen(room.saveAllWords(words))
                .observeOn(AndroidSchedulers.mainThread())
                //.andThen{ ormLite.saveAllWords(words) }
                .andThen(realm.saveAllWords(words) )

        //return ormLite.saveAllWords(words)
    }

}
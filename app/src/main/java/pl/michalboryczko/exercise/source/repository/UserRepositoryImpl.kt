package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.database.room.convertToTranslate
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.databases.CouchbaseDatabaseImpl
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import pl.michalboryczko.exercise.source.databases.OrmLiteDatabaseImpl
import timber.log.Timber

class UserRepositoryImpl (
        private var room: DatabaseOperations,
        private var realm: DatabaseOperations,
        private var couchbase: CouchbaseDatabaseImpl,
        private var ormLite: OrmLiteDatabaseImpl,


        private val checker: InternetConnectivityChecker
) :UserRepository, NetworkRepository(checker) {

    override fun getAllWords(): Single<List<Translate>> {
        return Single.defer { ormLite.fetchAllWords() }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        /*return room.saveAllWords(words)
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(realm.saveAllWords(words))
                .andThen(ormLite.saveAllWords(words))*/


        //return ormLite.saveAllWords(words)
        return Completable.defer { ormLite.saveAllWords(words) }
    }

}
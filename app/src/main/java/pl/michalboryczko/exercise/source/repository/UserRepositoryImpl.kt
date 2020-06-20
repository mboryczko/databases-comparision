package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.UserPreferences
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.rest.Api
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import pl.michalboryczko.exercise.source.databases.impl.*
import pl.michalboryczko.exercise.utils.WordParser
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl (
        private var api: Api,
        private var room: RoomDatabaseImpl,
        private var realm: RealmDatabaseImpl,
        private var couchbase: CouchbaseDatabaseImpl,
        private var ormLite: OrmLiteDatabaseImpl,
        private var objectBox: ObjectBoxDatabaseImpl,
        private var greenDao: GreenDaoDatabaseImpl,
        private val checker: InternetConnectivityChecker,
        private val preferences: UserPreferences
) :UserRepository, NetworkRepository(checker) {

    enum class DATABASE_IMPL{
        ROOM, REALM, OBJECTBOX, GREENDAO,
    }

    private var databaseImpl: DatabaseOperations = room

    private fun parseWords(): Single<List<Translate>>{
        return Single.defer { Single.just(WordParser.parseWords("dictionary.txt", 10000)) }
    }

    override fun deleteAllWords(): Completable {
        return Completable.defer { databaseImpl.deleteAll() }
    }

    override fun parseFirstNWords(n: Int): Completable {
        val words = WordParser.parseWords("dictionary.txt", n)
        return saveAllWords(words)
    }

    override fun changeDatabase(database: DATABASE_IMPL) {
        when(database){
            DATABASE_IMPL.ROOM ->  databaseImpl = room
            DATABASE_IMPL.REALM ->  databaseImpl = realm
            DATABASE_IMPL.OBJECTBOX ->  databaseImpl = objectBox
            DATABASE_IMPL.GREENDAO ->  databaseImpl = greenDao
        }
    }

    override fun getAllWords(): Single<List<Translate>> {
        return Single.defer { databaseImpl.fetchAllWords() }

        /*return Single.defer { objectBox.fetchAllWords() }
                .flatMap { room.fetchAllWords() }
                //.flatMap { ormLite.fetchAllWords() }
                .flatMap { realm.fetchAllWords() }*/
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        /*return room.saveAllWords(wordsToLearn)
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(realm.saveAllWords(wordsToLearn))
                .andThen(ormLite.saveAllWords(wordsToLearn))*/

        /*return Completable.defer { objectBox.saveAllWords(wordsToLearn) }
                .andThen(room.saveAllWords(wordsToLearn))
                .observeOn(AndroidSchedulers.mainThread())
                //.andThen{ ormLite.saveAllWords(wordsToLearn) }
                .andThen(realm.saveAllWords(wordsToLearn) )*/

        return Completable.defer { databaseImpl.saveAllWords(words) }
    }

    override fun searchWords(text: String): Single<List<Translate>> {
        return Single.defer { databaseImpl.searchWordsByPhrase(text) }
    }

    override fun searchWordsToLearn(text: String): Single<List<Translate>> {
        return databaseImpl.searchWordsToLearn(text)
    }

    override fun searchLearnedWords(text: String): Single<List<Translate>> {
        return databaseImpl.searchLearnedWords(text)
    }

    override fun updateAsAnsweredRight(word: Translate): Completable {
        return Completable.defer {
            word.timesAnsweredRight += 1
            databaseImpl.updateWords(listOf(word))
        }
    }

    override fun updateAsLearning(words: List<Translate>): Completable {
        return Completable.defer {
            val marked = words.map { it.apply {
                it.shouldBeLearned = true
            } }
            databaseImpl.updateWords(marked)
        }
    }

    override fun deleteWords(words: List<Translate>): Completable {
        return Completable.defer {
            databaseImpl.deleteWords(words)
        }
    }
}
package pl.michalboryczko.exercise.source.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.rest.Api
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import pl.michalboryczko.exercise.source.databases.impl.*
import pl.michalboryczko.exercise.utils.WordParser

class UserRepositoryImpl (
        private var api: Api,
        private var room: RoomDatabaseImpl,
        private var realm: RealmDatabaseImpl,
        private var couchbase: CouchbaseDatabaseImpl,
        private var ormLite: OrmLiteDatabaseImpl,
        private var objectBox: ObjectBoxDatabaseImpl,
        private val checker: InternetConnectivityChecker
) :UserRepository, NetworkRepository(checker) {

    private fun parseWords(): Single<List<Translate>>{
        return Single.defer { Single.just(WordParser.parseWords("dictionary.txt")) }
    }


    override fun getAllWords(): Single<List<Translate>> {
        return Single.defer { realm.fetchAllWords() }
                .flatMap {
                    if(it.isEmpty()){
                        //api.getWordsToLearn()
                        parseWords()
                                .flatMap { downloadedWords ->
                                    saveAllWords(downloadedWords)
                                            .toSingle { it }
                                }
                    }else{
                        Single.just(it)
                    }
                }

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

        return Completable.defer { realm.saveAllWords(words) }

        //return ormLite.saveAllWords(wordsToLearn)
    }

    override fun searchWords(text: String): Single<List<Translate>> {
        return Single.defer { realm.searchWords(text) }
    }

    override fun searchWordsToLearn(text: String): Flowable<List<Translate>> {
        return realm.searchWordsToLearn(text)
    }

    override fun searchLearnedWords(text: String): Flowable<List<Translate>> {
        return realm.searchLearnedWords(text)
    }

    override fun updateAsAnsweredRight(word: Translate): Completable {
        return Completable.defer {
            word.timesAnsweredRight += 1
            realm.updateWords(listOf(word))
        }
    }

    override fun updateAsLearning(words: List<Translate>): Completable {
        return Completable.defer {
            val marked = words.map { it.apply { it.shouldBeLearned = true } }
            realm.updateWords(marked)
        }
    }

    override fun deleteWords(words: List<Translate>): Completable {
        return Completable.defer {
            realm.deleteWords(words)
        }
    }
}
package pl.michalboryczko.exercise.source.databases.impl

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.oneOf
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.realm.convertToTranslate
import pl.michalboryczko.exercise.model.database.realm.convertToTranslateRealmList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber
import java.util.*

class RealmDatabaseImpl: DatabaseOperations(){

    override fun deleteAll(): Completable {
        val realm = Realm.getDefaultInstance()

        timer.startTimer()
        return Completable.create { completable ->
            realm.executeTransaction { bgRealm->
                bgRealm.deleteAll()
                Timber.d("realm deleteAll()")
                timer.stopTimer("realm deleteAll()")
                completable.onComplete()
            }
        }
    }

    override fun updateWords(words: List<Translate>): Completable{
        return saveAllWords(words)
    }

    override fun deleteWords(words: List<Translate>): Completable{
        val realm = Realm.getDefaultInstance()
        val idsList = words.map { it.id }.toTypedArray()

        timer.startTimer()
        return Completable.create { completable ->
            realm.executeTransaction { bgRealm->
                val wordsToDelete = bgRealm.where(TranslateRealm::class.java).oneOf("id", idsList).findAll()
                Timber.d("realm deleteWords() size: ${wordsToDelete.size}")
                wordsToDelete.deleteAllFromRealm()
                timer.stopTimer("realm deleteWords()")
                completable.onComplete()
            }
        }
    }

    override fun searchLearnedWords(text: String): Single<List<Translate>> {
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        val resultRealm = realm
                .where(TranslateRealm::class.java)
                .contains("english", text, Case.INSENSITIVE)
                .and()
                .equalTo("shouldBeLearned", true)
                .and()
                .greaterThan("timesAnsweredRight", 0.toInt())
                .findAll()

        timer.stopTimer("realm searchLearnedWords()")
        Timber.d("realm searchLearnedWords() size: ${resultRealm.size}")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(it.convertToTranslate()) }
        return Single.defer { Single.just(output) }
    }

    override fun searchWordsToLearn(text: String): Single<List<Translate>> {
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        val resultRealm = realm
                .where(TranslateRealm::class.java)
                .contains("english", text, Case.INSENSITIVE)
                .and()
                .equalTo("shouldBeLearned", true)
                .and()
                .equalTo("timesAnsweredRight", 0.toInt())
                .findAll()

        timer.stopTimer("realm searchWordsToLearn()")
        Timber.d("realm searchWordsToLearn() size: ${resultRealm.size}")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(it.convertToTranslate()) }
        return Single.defer { Single.just(output) }
    }

    override fun searchWordsByPhrase(text: String): Single<List<Translate>> {
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        val resultRealm = realm
                .where(TranslateRealm::class.java)
                .contains("english", text, Case.INSENSITIVE)
                .or()
                .contains("spanish", text, Case.INSENSITIVE)
                .findAll()

        timer.stopTimer("realm searchWordsByPhrase() for: $text")
        Timber.d("realm searchWordsByPhrase() size: ${resultRealm.size}")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(it.convertToTranslate()) }
        return Single.defer { Single.just(output) }
    }

    override fun fetchAllWords(): Single<List<Translate>>{
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        val resultRealm = realm.where(TranslateRealm::class.java).findAll()
        timer.stopTimer("realm fetchAllWords()")
        Timber.d("realm fetchAllWords() ${resultRealm.size}")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(it.convertToTranslate()) }
        return Single.defer { Single.just(output) }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val realm = Realm.getDefaultInstance()
        val realmList = words.convertToTranslateRealmList()

        timer.startTimer()

        return Completable.create { completable ->
            realm.executeTransaction { bgRealm->
                bgRealm.insertOrUpdate(realmList)
                timer.stopTimer("realm saveAllWords()")
                completable.onComplete()
            }
        }
    }
}
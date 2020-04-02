package pl.michalboryczko.exercise.source.databases.impl

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.Case
import io.realm.Realm
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.realm.convertToTranslate
import pl.michalboryczko.exercise.model.database.realm.convertToTranslateRealmList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber

class RealmDatabaseImpl: DatabaseOperations(){

    fun updateWords(words: List<Translate>): Completable{
        return saveAllWords(words)
    }

    fun searchWordsToLearn(text: String): Flowable<List<Translate>> {
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        return realm.asFlowable()
                .map { r ->
                    r.where(TranslateRealm::class.java)
                            .contains("english", text, Case.INSENSITIVE)
                            .and()
                            .equalTo("shouldBeLearned", true)
                            .findAll()

                }
                .map {
                    timer.stopTimer("realm searchWordsToLearn()")
                    it
                }
                .map {realmResult-> realmResult.map { it.convertToTranslate() } }


        /*val realm = Realm.getDefaultInstance()

        val resultRealm = realm
                .where(TranslateRealm::class.java)
                *//*.contains("english", text, Case.INSENSITIVE)
                .or()
                .contains("spanish", text, Case.INSENSITIVE)
                .and()*//*
                .equalTo("shouldBeLearned", true)
                .findAll()

        timer.stopTimer("realm searchWordsToLearn()")
        Timber.d("realm searchWordsToLearn() size: ${resultRealm.size}")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(it.convertToTranslate()) }
        return Single.defer { Single.just(output) }*/
    }

    override fun searchWords(text: String): Single<List<Translate>> {
        val realm = Realm.getDefaultInstance()

       /* timer.startTimer()
        realm.executeTransaction {bgRealm->
            val resultRealm = bgRealm
                    .where(TranslateRealm::class.java)
                    .contains("english", text, Case.INSENSITIVE)
                    .or()
                    .contains("spanish", text, Case.INSENSITIVE)
                    .findAll()

            timer.stopTimer("realm searchWords()")
            Timber.d("realm searchWords() size: ${resultRealm.size}")
        }

        return Single.defer { Single.just() }*/


        val resultRealm = realm
                .where(TranslateRealm::class.java)
                .contains("english", text, Case.INSENSITIVE)
                .or()
                .contains("spanish", text, Case.INSENSITIVE)
                .findAll()

        timer.stopTimer("realm searchWords()")
        Timber.d("realm searchWords() size: ${resultRealm.size}")

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
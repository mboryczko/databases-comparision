package pl.michalboryczko.exercise.source.databases

import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import pl.michalboryczko.exercise.model.database.realm.TranslateRealm
import pl.michalboryczko.exercise.model.database.realm.convertToTranslateRealmList
import pl.michalboryczko.exercise.model.presentation.Translate

class RealmDatabaseImpl: DatabaseOperations(){


    override fun fetchAllWords(): Single<List<Translate>>{
        val realm = Realm.getDefaultInstance()
        timer.startTimer()
        val resultRealm = realm.where(TranslateRealm::class.java).findAll()
        timer.stopTimer("realm fetchAllWords()")

        val output = mutableListOf<Translate>()
        resultRealm.forEach { output.add(Translate(it.english, it.spanish)) }
        return Single.defer { Single.just(output) }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val realm = Realm.getDefaultInstance()
        val realmList = words.convertToTranslateRealmList()

        timer.startTimer()
        return Completable.create {completable->
            realm.executeTransactionAsync(
                    { bgRealm ->
                        bgRealm.insertOrUpdate(realmList)
                    },
                    {
                        completable.onComplete()
                        timer.stopTimer("realm saveAllWords()")
                    },
                    { error ->
                        completable.onError(error)
                    }
            )
        }
    }
}
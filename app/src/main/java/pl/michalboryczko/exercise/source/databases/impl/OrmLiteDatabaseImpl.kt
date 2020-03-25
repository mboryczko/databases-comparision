package pl.michalboryczko.exercise.source.databases.impl

import android.os.Looper
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.model.database.ormlite.OrmLiteDBHelper
import pl.michalboryczko.exercise.model.database.ormlite.TranslateOrmLite
import pl.michalboryczko.exercise.model.database.ormlite.convertToTranslateOrmLiteList
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber
import java.sql.SQLException


class OrmLiteDatabaseImpl(val ormLiteDbHelper: OrmLiteDBHelper): DatabaseOperations(){

    override fun fetchAllWords(): Single<List<Translate>>{
        timer.startTimer()
        Timber.d("ormLiteDatabaseImpl fetchAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        val translatesOrmLite = mutableListOf<TranslateOrmLite>()
        try {
            translatesOrmLite.addAll(ormLiteDbHelper.getAll(TranslateOrmLite::class.java))
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        timer.stopTimer("ormLiteDatabaseImpl fetchAllWords")

        val output = mutableListOf<Translate>()
        translatesOrmLite.forEach { output.add(Translate(it.english, it.spanish)) }

        return Single.create<List<Translate>> { emitter ->
            emitter.onSuccess(output)
        }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val ormLiteList = words.convertToTranslateOrmLiteList()

        Timber.d("ormLiteDatabaseImpl saveAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        try {
            ormLiteList .forEach {
                //Timber.d("ormLiteDatabaseImpl saveAllWords iterate: ${it.english} - ${it.spanish}")
                ormLiteDbHelper.create(it)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            Timber.d("ormLiteDatabaseImpl saveAllWords e: ${e.message}")
            Timber.d("ormLiteDatabaseImpl saveAllWords e: ${e.errorCode}")
        }

        timer.stopTimer("ormLiteDatabaseImpl saveAllWords")
        return Completable.complete()
    }
}
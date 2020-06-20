package pl.michalboryczko.exercise.source.databases.impl

import android.os.Looper
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.model.database.ormlite.OrmLiteDBHelper
import pl.michalboryczko.exercise.model.database.ormlite.TranslateOrmLite
import pl.michalboryczko.exercise.model.database.ormlite.convertToTranslate
import pl.michalboryczko.exercise.model.database.ormlite.convertToTranslateOrmLiteList
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber
import java.sql.SQLException


class OrmLiteDatabaseImpl(val ormLiteDbHelper: OrmLiteDBHelper): DatabaseOperations(){
    override fun deleteAll(): Completable {
        timer.startTimer()
        ormLiteDbHelper.deleteAll(TranslateOrmLite::class.java)
        timer.stopTimer("ormLiteDbHelper deleteAll")
        return Completable.complete()
    }

    override fun updateWords(words: List<Translate>): Completable {
        val ormLiteList = words.convertToTranslateOrmLiteList()
        timer.startTimer()
        ormLiteDbHelper.fillObjects(TranslateOrmLite::class.java, ormLiteList)
        timer.stopTimer("ormLiteDbHelper updateWords")
        return Completable.complete()
    }

    override fun deleteWords(words: List<Translate>): Completable {
        val ormLiteList = words.convertToTranslateOrmLiteList()
        timer.startTimer()
        ormLiteList .forEach { ormLiteDbHelper.deleteObjects(TranslateOrmLite::class.java, ormLiteList) }
        timer.stopTimer("ormLiteDbHelper deleteWords")
        return Completable.complete()
    }

    override fun searchWordsToLearn(text: String): Single<List<Translate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchLearnedWords(text: String): Single<List<Translate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchWordsByPhrase(text: String): Single<List<Translate>> {
        timer.startTimer()
        Timber.d("ormLiteDatabaseImpl searchWordsByPhrase MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        val translatesOrmLite = mutableListOf<TranslateOrmLite>()
        try {
            translatesOrmLite.addAll(ormLiteDbHelper.getAll(TranslateOrmLite::class.java))
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        timer.stopTimer("ormLiteDatabaseImpl searchWordsByPhrase($text)")

        val output = mutableListOf<Translate>()
        translatesOrmLite.forEach { output.add(it.convertToTranslate()) }

        return Single.create<List<Translate>> { emitter ->
            emitter.onSuccess(output)
        }
    }

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
        translatesOrmLite.forEach { output.add(it.convertToTranslate()) }

        return Single.create<List<Translate>> { emitter ->
            emitter.onSuccess(output)
        }
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val ormLiteList = words.convertToTranslateOrmLiteList()

        Timber.d("ormLiteDatabaseImpl saveAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        ormLiteDbHelper.fillObjects(TranslateOrmLite::class.java, ormLiteList)

        timer.stopTimer("ormLiteDatabaseImpl saveAllWords")
        return Completable.complete()
    }
}
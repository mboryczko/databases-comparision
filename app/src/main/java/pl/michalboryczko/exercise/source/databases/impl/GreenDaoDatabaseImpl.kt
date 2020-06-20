package pl.michalboryczko.exercise.source.databases.impl

import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.greendao.TranslateGreenDao
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.model.presentation.convertToTranslate
import pl.michalboryczko.exercise.model.presentation.convertToTranslateGreen
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber

class GreenDaoDatabaseImpl(val greenDao: TranslateGreenDao): DatabaseOperations(){

    override fun updateWords(words: List<Translate>): Completable {

        val greenList = words.map { it.convertToTranslateGreen() }
        timer.startTimer()
        greenDao.updateInTx(greenList)
        timer.stopTimer("greendao updateWords")
        return Completable.complete()
    }

    override fun deleteWords(words: List<Translate>): Completable {
        val keys = words.map { it.id }
        timer.startTimer()
        greenDao.deleteByKeyInTx(keys)
        timer.stopTimer("greendao deleteWords")
        return Completable.complete()
    }

    override fun fetchAllWords(): Single<List<Translate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchWordsToLearn(text: String): Single<List<Translate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchLearnedWords(text: String): Single<List<Translate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchWordsByPhrase(text: String): Single<List<Translate>> {
        val like = "%$text%"
        timer.startTimer()
        val qb = greenDao
                .queryBuilder()
                //.where(TranslateGreenDao.Properties.English.eq("what"))
                //.where(TranslateGreenDao.Properties.English.like(text))
                .whereOr(TranslateGreenDao.Properties.English.like(like), TranslateGreenDao.Properties.English.like(like))
                .build()

        val elo = qb.list()

        timer.stopTimer("greendao searchWordsByPhrase($text)")

        val output = elo.toList().map { it.convertToTranslate() }
        Timber.d("greendao size searchWordsByPhrase: ${output.size}")
        return Single.just(output)
    }

    override fun deleteAll(): Completable {
        timer.startTimer()
        greenDao.deleteAll()
        timer.stopTimer("greendao deleteAll")
        return Completable.complete()
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val greenList = words.map { it.convertToTranslateGreen() }
        timer.startTimer()
        greenDao.insertInTx(greenList)
        timer.stopTimer("greendao saveAllWords")
        return Completable.complete()
    }
}
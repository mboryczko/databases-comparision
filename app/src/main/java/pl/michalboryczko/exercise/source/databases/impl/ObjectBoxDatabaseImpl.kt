package pl.michalboryczko.exercise.source.databases.impl

import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.query.QueryBuilder
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.objectbox.*
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber


class ObjectBoxDatabaseImpl(): DatabaseOperations(){
    val wordsBox: Box<TranslateObjectBox> = ObjectBox.boxStore.boxFor()

    override fun deleteAll(): Completable {
        timer.startTimer()
        wordsBox.removeAll()
        timer.stopTimer("objectbox deleteAll")
        return Completable.complete()
    }

    override fun updateWords(words: List<Translate>): Completable {
        return saveAllWords(words)
    }

    override fun deleteWords(words: List<Translate>): Completable {
        val objectBoxList = words.convertToTranslateObjectBoxLiteList()
        timer.startTimer()
        wordsBox.remove(objectBoxList)
        timer.stopTimer("objectbox deleteWords")
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
        val words: List<TranslateObjectBox> = wordsBox
                .query()
                .contains(TranslateObjectBox_.english, text, QueryBuilder.StringOrder.CASE_INSENSITIVE)
                .or()
                .equal(TranslateObjectBox_.spanish, text, QueryBuilder.StringOrder.CASE_INSENSITIVE)
                .build().find()
        timer.stopTimer("objectbox searchWordsByPhrase($text)")

        val output = mutableListOf<Translate>()
        words.forEach { output.add(it.convertToTranslate()) }

        Timber.d("objectbox size searchWordsByPhrase: ${output.size}")
        return Single.just(output)
    }

    override fun fetchAllWords(): Single<List<Translate>>{
        timer.startTimer()
        val words: List<TranslateObjectBox> = wordsBox.query().build().find()
        timer.stopTimer("objectbox fetchAllWords")

        val output = mutableListOf<Translate>()
        words.forEach { output.add(it.convertToTranslate()) }

        Timber.d("objectbox size fetched: ${output.size}")
        return Single.just(output)
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val objectBoxList = words.convertToTranslateObjectBoxLiteList()

        timer.startTimer()
        wordsBox.put(objectBoxList)
        timer.stopTimer("objectbox saveAllWords")

        return Completable.complete()
    }
}
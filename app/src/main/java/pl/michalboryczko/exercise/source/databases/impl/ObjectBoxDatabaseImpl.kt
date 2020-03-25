package pl.michalboryczko.exercise.source.databases.impl

import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.objectbox.ObjectBox
import pl.michalboryczko.exercise.model.database.objectbox.TranslateObjectBox
import pl.michalboryczko.exercise.model.database.objectbox.convertToTranslateObjectBoxLiteList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber


class ObjectBoxDatabaseImpl(): DatabaseOperations(){
    val wordsBox: Box<TranslateObjectBox> = ObjectBox.boxStore.boxFor()

    override fun fetchAllWords(): Single<List<Translate>>{
        timer.startTimer()
        val words: List<TranslateObjectBox> = wordsBox.query().build().find()
        timer.stopTimer("objectbox fetchAllWords")

        val output = mutableListOf<Translate>()
        words.forEach { output.add(Translate(it.english, it.spanish)) }

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
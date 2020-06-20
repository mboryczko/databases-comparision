package pl.michalboryczko.exercise.source.databases.impl

import android.os.Looper
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.model.database.room.TranslateRoom
import pl.michalboryczko.exercise.model.database.room.convertToTranslate
import pl.michalboryczko.exercise.model.database.room.convertToTranslateRoomList
import pl.michalboryczko.exercise.model.presentation.Translate
import pl.michalboryczko.exercise.source.databases.DatabaseOperations
import timber.log.Timber

class RoomDatabaseImpl(val roomDatabase: RoomDatabase) : DatabaseOperations(){

    override fun deleteAll(): Completable {
        Timber.d("room deleteAll MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        return roomDatabase
                .translateDAO()
                .deleteAllWords()
                .doOnComplete { timer.stopTimer("room deleteAll()") }
                .doOnError { Timber.d("room onError ${it.message}") }
    }


    override fun updateWords(words: List<Translate>): Completable {
        val roomList = words.convertToTranslateRoomList()
        Timber.d("room updateWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        return roomDatabase
                .translateDAO()
                .updateWords(roomList)
                .doOnComplete { timer.stopTimer("room updateWords()") }
                .doOnError { Timber.d("room onError ${it.message}") }
    }

    override fun deleteWords(words: List<Translate>): Completable {
        val roomList = words.convertToTranslateRoomList()
        Timber.d("room deleteWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        return roomDatabase
                .translateDAO()
                .deleteWords(roomList)
                .doOnComplete { timer.stopTimer("room deleteWords()") }
                .doOnError { Timber.d("room onError ${it.message}") }
    }

    override fun searchLearnedWords(text: String): Single<List<Translate>> {
        Timber.d("room searchLearnedWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        return roomDatabase
                .translateDAO()
                .searchLearnedWords()
                .doOnSubscribe { timer.startTimer() }
                .doOnSuccess {
                    timer.stopTimer("room searchLearnedWords()")
                    Timber.d("room searchLearnedWords() ${it.size}")
                }
                .toObservable()
                .flatMapIterable { it }
                .map { it.convertToTranslate()}
                .toList()
    }

    override fun searchWordsToLearn(text: String): Single<List<Translate>> {
        Timber.d("room searchWordsToLearn MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        return roomDatabase
                .translateDAO()
                .searchWordsToLearn()
                .doOnSubscribe { timer.startTimer() }
                .doOnSuccess {
                    timer.stopTimer("room searchWordsToLearn()")
                    Timber.d("room searchWordsToLearn() ${it.size}")
                }
                .toObservable()
                .flatMapIterable { it }
                .map { it.convertToTranslate()}
                .toList()
    }

    override fun searchWordsByPhrase(text: String): Single<List<Translate>> {
        Timber.d("room searchWordsByPhrase MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        return roomDatabase
                .translateDAO()
                .searchWordsByPhrase("%$text%")
                .doOnSubscribe { timer.startTimer() }
                .doOnSuccess {
                    timer.stopTimer("room searchWordsByPhrase($text)")
                    Timber.d("room searchWordsByPhrase() ${it.size}")
                }
                .toObservable()
                .flatMapIterable { it }
                .map { it.convertToTranslate()}
                .toList()
    }

    override fun fetchAllWords(): Single<List<Translate>> {
        Timber.d("room fetchAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        return roomDatabase
                .translateDAO()
                .queryTranslates()
                .doOnSubscribe { timer.startTimer() }
                .doOnSuccess {
                    timer.stopTimer("room fetchAllWords()")
                    Timber.d("room fetchAllWords() ${it.size}")
                }
                .toObservable()
                .flatMapIterable { it }
                .map { it.convertToTranslate()}
                .toList()
    }

    override fun saveAllWords(words: List<Translate>): Completable {
        val roomList = words.convertToTranslateRoomList()
        Timber.d("room saveAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        timer.startTimer()
        return roomDatabase
                .translateDAO()
                .insertTranslate(roomList)
                .doOnComplete { timer.stopTimer("room saveAllWords()") }
                .doOnError { Timber.d("room onError ${it.message}") }

    }
}
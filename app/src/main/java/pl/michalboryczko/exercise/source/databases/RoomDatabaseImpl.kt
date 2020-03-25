package pl.michalboryczko.exercise.source.databases

import android.os.Looper
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.model.database.room.convertToTranslateRoomList
import pl.michalboryczko.exercise.model.presentation.Translate
import timber.log.Timber

class RoomDatabaseImpl(val roomDatabase: RoomDatabase) : DatabaseOperations(){
    override fun fetchAllWords(): Single<List<Translate>> {

        Timber.d("room fetchAllWords MAINTHREAD: ${Looper.myLooper() == Looper.getMainLooper()}")
        return roomDatabase
                .translateDAO()
                .queryTranslates()
                .doOnSubscribe { timer.startTimer() }
                .doOnSuccess { timer.stopTimer("room fetchAllWords()") }
                .toObservable()
                .flatMapIterable { it }
                .map { Translate(it.english, it.spanish) }
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

    }
}
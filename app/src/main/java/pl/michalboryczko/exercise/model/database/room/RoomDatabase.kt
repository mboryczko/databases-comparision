package pl.michalboryczko.exercise.model.database.room

import androidx.room.RoomDatabase
import androidx.room.Database
import pl.michalboryczko.exercise.model.database.room.TranslateDAO
import pl.michalboryczko.exercise.model.database.room.TranslateRoom

@Database(entities = arrayOf(TranslateRoom::class), version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun translateDAO(): TranslateDAO
}
package pl.michalboryczko.exercise.model.database.room

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = arrayOf(TranslateRoom::class), version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun translateDAO(): TranslateRoomDAO
}
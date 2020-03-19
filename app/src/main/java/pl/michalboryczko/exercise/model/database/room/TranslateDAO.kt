package pl.michalboryczko.exercise.model.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TranslateDAO{

    @Query("SELECT * FROM ${TranslateRoom.TABLE_NAME}")
    fun queryTranslates(): Single<List<TranslateRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTranslate(translates: List<TranslateRoom>): Completable

}
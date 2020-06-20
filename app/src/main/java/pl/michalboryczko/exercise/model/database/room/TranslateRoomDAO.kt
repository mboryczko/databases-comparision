package pl.michalboryczko.exercise.model.database.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import pl.michalboryczko.exercise.model.presentation.Translate

@Dao
interface TranslateRoomDAO{

    @Query("SELECT * FROM ${TranslateRoom.TABLE_NAME}")
    fun queryTranslates(): Single<List<TranslateRoom>>

    @Query("SELECT * FROM ${TranslateRoom.TABLE_NAME} WHERE timesAnsweredRight > 0")
    fun searchLearnedWords(): Single<List<TranslateRoom>>

    @Query("SELECT * FROM ${TranslateRoom.TABLE_NAME} WHERE shouldBeLearned = 1")
    fun searchWordsToLearn(): Single<List<TranslateRoom>>

    @Query("SELECT * FROM ${TranslateRoom.TABLE_NAME} WHERE spanish like :text or english like :text")
    fun searchWordsByPhrase(text: String): Single<List<TranslateRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTranslate(translates: List<TranslateRoom>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWords(translates: List<TranslateRoom>): Completable

    @Delete
    fun deleteWords(words: List<TranslateRoom>): Completable

    @Query("DELETE FROM ${TranslateRoom.TABLE_NAME}")
    fun deleteAllWords(): Completable




}
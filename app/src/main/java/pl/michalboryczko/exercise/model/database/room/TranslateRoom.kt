package pl.michalboryczko.exercise.model.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.michalboryczko.exercise.model.presentation.Translate


@Entity(tableName = TranslateRoom.TABLE_NAME)
data class TranslateRoom(
        @PrimaryKey(autoGenerate = false)
        val id: Long,
        val english: String,
        val spanish: String,
        var timesAnsweredRight: Int = 0,
        var timesAnsweredWrong: Int = 0,
        var shouldBeLearned: Boolean = false
){
    companion object{
        const val TABLE_NAME="word"
    }
}

fun TranslateRoom.convertToTranslate(): Translate{
    return Translate(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}


fun List<Translate>.convertToTranslateRoomList(): List<TranslateRoom>{
    val translateRoomList = mutableListOf<TranslateRoom>()
    this.forEachIndexed{ index, translate ->
        val translateRoom = TranslateRoom(
                id = translate.id,
                english = translate.english,
                spanish = translate.spanish,
                timesAnsweredRight = 0,
                timesAnsweredWrong = 0,
                shouldBeLearned = false
        )
        translateRoomList.add(translateRoom)
    }

    return translateRoomList
}

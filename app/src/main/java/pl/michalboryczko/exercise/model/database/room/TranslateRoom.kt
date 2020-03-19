package pl.michalboryczko.exercise.model.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.michalboryczko.exercise.model.presentation.Translate


@Entity(tableName = TranslateRoom.TABLE_NAME)
data class TranslateRoom(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val english: String,
        val spanish: String
){
    companion object{
        const val TABLE_NAME="word"
    }
}

fun TranslateRoom.convertToTranslate(): Translate{
    return Translate(this.english, this.spanish)
}


fun List<Translate>.convertToTranslateRoomList(): List<TranslateRoom>{
    val translateRoomList = mutableListOf<TranslateRoom>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(TranslateRoom(id = index, english = translate.english, spanish = translate.spanish))
    }

    return translateRoomList
}

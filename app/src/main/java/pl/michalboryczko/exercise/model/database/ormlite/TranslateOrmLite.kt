package pl.michalboryczko.exercise.model.database.ormlite

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import pl.michalboryczko.exercise.model.presentation.Translate


@DatabaseTable(tableName = "words")
open class TranslateOrmLite(
        @DatabaseField(generatedId = true)
        var id: Long = 0,
        @DatabaseField(canBeNull = false)
        var english: String = "",
        @DatabaseField(canBeNull = false)
        var spanish: String = "",
        @DatabaseField(canBeNull = false)
        var timesAnsweredRight: Int = 0,
        @DatabaseField(canBeNull = false)
        var timesAnsweredWrong: Int = 0,
        @DatabaseField(canBeNull = false)
        var shouldBeLearned: Boolean = false
)

fun TranslateOrmLite.convertToTranslate(): Translate{
    return Translate(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}


fun List<Translate>.convertToTranslateOrmLiteList(): List<TranslateOrmLite>{
    val translateRoomList = mutableListOf<TranslateOrmLite>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(
                TranslateOrmLite(
                        id = translate.id,
                        english = translate.english,
                        spanish = translate.spanish,
                        timesAnsweredRight = 0,
                        timesAnsweredWrong = 0,
                        shouldBeLearned = false
                )
        )
    }

    return translateRoomList
}

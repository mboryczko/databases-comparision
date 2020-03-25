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
        var spanish: String = ""
)

fun TranslateOrmLite.convertToTranslate(): Translate{
    return Translate(this.english, this.spanish)
}


fun List<Translate>.convertToTranslateOrmLiteList(): List<TranslateOrmLite>{
    val translateRoomList = mutableListOf<TranslateOrmLite>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(TranslateOrmLite(id = index.toLong(), english = translate.english, spanish = translate.spanish))
    }

    return translateRoomList
}

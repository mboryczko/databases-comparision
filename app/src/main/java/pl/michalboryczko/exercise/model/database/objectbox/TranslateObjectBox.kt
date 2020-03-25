package pl.michalboryczko.exercise.model.database.objectbox


import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import pl.michalboryczko.exercise.model.presentation.Translate


@Entity
open class TranslateObjectBox(
        @Id(assignable = true) var id: Long = 0,
        var english: String = "",
        var spanish: String = ""
)

fun TranslateObjectBox.convertToTranslate(): Translate{
    return Translate(this.english, this.spanish)
}


fun List<Translate>.convertToTranslateObjectBoxLiteList(): List<TranslateObjectBox>{
    val translateRoomList = mutableListOf<TranslateObjectBox>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(TranslateObjectBox(id = index.toLong(), english = translate.english, spanish = translate.spanish))
    }

    return translateRoomList
}

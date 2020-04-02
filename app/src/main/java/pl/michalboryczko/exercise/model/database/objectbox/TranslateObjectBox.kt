package pl.michalboryczko.exercise.model.database.objectbox


import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import pl.michalboryczko.exercise.model.database.realm.convertToTranslate
import pl.michalboryczko.exercise.model.presentation.Translate


@Entity
open class TranslateObjectBox(
        @Id(assignable = true) var id: Long = 0,
        var english: String = "",
        var spanish: String = "",
        var timesAnsweredRight: Int = 0,
        var timesAnsweredWrong: Int = 0,
        var shouldBeLearned: Boolean = false
)

fun TranslateObjectBox.convertToTranslate(): Translate{
    return Translate(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}


fun List<Translate>.convertToTranslateObjectBoxLiteList(): List<TranslateObjectBox>{
    val translateRoomList = mutableListOf<TranslateObjectBox>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(
                TranslateObjectBox(
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

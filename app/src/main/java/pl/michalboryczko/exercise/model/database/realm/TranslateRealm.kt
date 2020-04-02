package pl.michalboryczko.exercise.model.database.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.michalboryczko.exercise.model.presentation.Translate


open class TranslateRealm(
        @PrimaryKey var id: Long = 0,
        var english: String = "",
        var spanish: String = "",
        var timesAnsweredRight: Int = 0,
        var timesAnsweredWrong: Int = 0,
        var shouldBeLearned: Boolean = false
): RealmObject()

fun TranslateRealm.convertToTranslate(): Translate{
    return Translate(this.id, this.english, this.spanish, this.timesAnsweredRight, this.timesAnsweredWrong, this.shouldBeLearned)
}

fun List<Translate>.convertToTranslateRealmList(): List<TranslateRealm>{
    val translateRoomList = mutableListOf<TranslateRealm>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(
                TranslateRealm(
                        id = translate.id,
                        english = translate.english,
                        spanish = translate.spanish,
                        timesAnsweredRight = translate.timesAnsweredRight,
                        timesAnsweredWrong = translate.timesAnsweredWrong,
                        shouldBeLearned = translate.shouldBeLearned
                )
        )
    }

    return translateRoomList
}

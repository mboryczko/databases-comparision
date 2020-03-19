package pl.michalboryczko.exercise.model.database.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.michalboryczko.exercise.model.presentation.Translate


open class TranslateRealm(
        @PrimaryKey var id: Long = 0,
        var english: String = "",
        var spanish: String = ""
): RealmObject()

fun TranslateRealm.convertToTranslate(): Translate{
    return Translate(this.english, this.spanish)
}



fun List<Translate>.convertToTranslateRealmList(): List<TranslateRealm>{
    val translateRoomList = mutableListOf<TranslateRealm>()
    this.forEachIndexed{ index, translate ->
        translateRoomList.add(TranslateRealm(id = index.toLong(), english = translate.english, spanish = translate.spanish))
    }

    return translateRoomList
}

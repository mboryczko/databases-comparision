package pl.michalboryczko.exercise.model.database.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.michalboryczko.exercise.model.presentation.Translate


open class DoctorRealm(
        @PrimaryKey var id: Long = 0,
        var name: String = "",
        var specializations: RealmList<Specialization> = RealmList()
): RealmObject()



open class Specialization(
        @PrimaryKey var id: Long = 0,
        var name: String = ""
): RealmObject()

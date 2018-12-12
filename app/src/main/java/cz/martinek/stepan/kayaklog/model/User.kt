package cz.martinek.stepan.kayaklog.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User() : RealmObject() {

    @PrimaryKey
    var username: String? = null
    var trips: RealmList<Trip>? = null
    var achievements: RealmList<AcquiredAchievement>? = null
}
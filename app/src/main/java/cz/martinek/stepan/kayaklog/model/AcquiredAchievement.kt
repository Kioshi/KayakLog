package cz.martinek.stepan.kayaklog.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class AcquiredAchievement() : RealmObject() {
    @PrimaryKey
    var guid: String? = null
    var achievementId: Int? = null
    var extraInfo: String = ""
    var acquiredTime: Date? = null
}

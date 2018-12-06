package cz.martinek.stepan.kayaklog.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.*


open class Trip() : RealmObject() {
    @PrimaryKey
    var guid: String? = null

    @Index
    var timeCreated: Date? = null
    var duration: Int = 0
    var path: RealmList<Path>? = null
    var publiclyAvailable: Boolean = false
    var desc: String = ""

    @Index
    var name: String = "Trip: ${timeCreated}"
}


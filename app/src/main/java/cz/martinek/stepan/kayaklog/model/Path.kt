package cz.martinek.stepan.kayaklog.model

import io.realm.RealmObject

open class Path() : RealmObject() {
    var pos: Int = 0
    var lat: Double = 0.0
    var long: Double = 0.0

}

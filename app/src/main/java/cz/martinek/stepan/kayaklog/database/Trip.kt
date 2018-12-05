package cz.martinek.stepan.kayaklog.database

import cz.martinek.stepan.kayaklog.model.Path
import java.util.*


class Trip {

    var id: Int = 0
    var desc: String? = null
    var name: String? = null
    var public: Boolean? = null
    var duration: Int? = null
    var timeCreated: Date? = null// "2018-11-26T12:14:15.000Z",
    var path: List<Path>? = null

/*
    constructor(id: Int, desc: String, name: String, public: Boolean, duration: Int, timeCreated: Date, path: List<Path>) {
        this.id = id
        this.desc = desc
        this.name = name
        this.public = public
        this.duration = duration
        this.timeCreated = timeCreated
        this.path = path
    }
*/
    constructor(desc: String, name: String, public: Boolean, duration: Int, timeCreated: Date, path: List<Path>) {
        this.name = name
        this.desc = desc
        this.public = public
        this.duration = duration
        this.timeCreated = timeCreated
        this.path = path
    }
}


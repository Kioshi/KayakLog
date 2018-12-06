package cz.martinek.stepan.kayaklog.database

import cz.martinek.stepan.kayaklog.model.Path
import java.util.*


data class Trip(

    var guid: String,
    var timeCreated: Date,// "2018-11-26T12:14:15.000Z",
    var duration: Int,
    var path: List<Path>,
    var public: Boolean = false,
    var desc: String = "",
    var name: String = "Trip: ${timeCreated}"
)
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

    constructor(desc: String, name: String, public: Boolean, duration: Int, timeCreated: Date, path: List<Path>) {
        this.name = name
        this.desc = desc
        this.public = public
        this.duration = duration
        this.timeCreated = timeCreated
        this.path = path
    }
}
*/

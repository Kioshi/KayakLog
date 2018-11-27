package cz.martinek.stepan.kayaklog.model

import java.util.*

data class Trip(
    val guid: String,
    var description: String,
    var name: String,
    var public: Boolean,
    val duration: Int,
    val timeCreated: Date,// "2018-11-26T12:14:15.000Z",
    var path: List<Path>
)

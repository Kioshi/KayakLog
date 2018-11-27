package cz.martinek.stepan.kayaklog.model

import java.util.*

data class AcquiredAchievement(
    val guid:String,
    val achievementId: Int,
    val extraInfo: String,
    val acquiredTime: Date//: "2018-11-26T12:14:15.000Z"
)

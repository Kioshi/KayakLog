package cz.martinek.stepan.kayaklog.model

import cz.martinek.stepan.kayaklog.database.Trip

data class User(
        val id: Int,
        var trips: List<Trip>,
        var achievements: List<AcquiredAchievement>
)
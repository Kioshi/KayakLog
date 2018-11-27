package cz.martinek.stepan.kayaklog.model

data class User(
        val id: Int,
        var trips: List<Trip>,
        var achievements: List<AcquiredAchievement>
)
package cz.martinek.stepan.kayaklog.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userdata_table")
data class UserData(
    @PrimaryKey @ColumnInfo(name = "username") val username: String)

   /*

    var uid: Int = 0,

    var trips: String?,

    var archievements: String?

)
        */
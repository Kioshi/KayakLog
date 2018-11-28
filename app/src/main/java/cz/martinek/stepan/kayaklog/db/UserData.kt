package cz.martinek.stepan.kayaklog.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String?
)
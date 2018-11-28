package cz.martinek.stepan.kayaklog.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity

data class UserData(
@PrimaryKey var uid: Int,
@ColumnInfo(name = "first_name") var firstName: String?



/*
data class UserData(@PrimaryKey(autoGenerate = true) var id: Long?,

                   //val name: String





@ColumnInfo(name = "username") var username: String,
@ColumnInfo(name = "longditude") var longditude: Double,
@ColumnInfo(name = "latidude") var latidude: Double

*/
)
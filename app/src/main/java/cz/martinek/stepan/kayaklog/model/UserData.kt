package cz.martinek.stepan.kayaklog.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "UserData")

data class UserData(@PrimaryKey(autoGenerate = true) var id: Long?,

                   //val name: String





@ColumnInfo(name = "username") var username: String,
@ColumnInfo(name = "longditude") var longditude: Double,
@ColumnInfo(name = "latidude") var latidude: Double


)
package cz.martinek.stepan.kayaklog.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface UserDao {


    @Insert
    fun insert(userdata: UserData)

    @Query("SELECT * FROM userdata_table")
    fun getAllUserData(): LiveData<List<UserData>>
/*
    @Update
    fun updateUserData(userdata: UserData)

    @Delete
    fun deleteUserData(userdata: UserData)

    @Query("SELECT * FROM UserData WHERE username == :name")
    fun getNameByName(name: String): List<UserData>

    @Query("SELECT * FROM UserData")
    fun getUserData(): List<UserData>

        @get:Query("SELECT * FROM UserData")
    val getAll: List<UserData>

*/
}
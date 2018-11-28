package cz.martinek.stepan.kayaklog.model

import android.arch.persistence.room.*

@Dao
interface DAO {

    @Query("SELECT * FROM UserData")
    fun getAll(): List<UserData>


    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(userdata: UserData)

    @Update
    fun updateUserData(userdata: UserData)

    @Delete
    fun deleteUserData(userdata: UserData)

    @Query("SELECT * FROM UserData WHERE username == :name")
    fun getNameByName(name: String): List<UserData>

    @Query("SELECT * FROM UserData")
    fun getUserData(): List<UserData>
*/
}
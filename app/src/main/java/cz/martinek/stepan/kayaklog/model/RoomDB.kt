package cz.martinek.stepan.kayaklog.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context



@Database(entities = arrayOf(UserData::class), version = 1)
abstract class RoomDB : RoomDatabase() {


    abstract fun dao() : DAO


    companion object {
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB?{
            if(INSTANCE == null){
                synchronized(RoomDB::class){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RoomDB::class.java,"roomdb.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}
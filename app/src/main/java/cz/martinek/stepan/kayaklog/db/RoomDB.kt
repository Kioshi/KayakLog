package cz.martinek.stepan.kayaklog.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch
import java.time.chrono.HijrahChronology.INSTANCE


@Database(entities = [UserData::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao


    companion object {




        /**
         * The only instance
         */


        private var sInstance: AppDatabase? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {

                sInstance = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "KayakDB")
                        .fallbackToDestructiveMigration()
                    //.addCallback(AppDatabaseCallback(scope))
                        .build()
            }
            return sInstance!!
        }


    }
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            sInstance?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.userDao())
                }
            }
        }
    }

}
fun populateDatabase(userDao: UserDao) {
    //userDao.deleteAll()

    var username = UserData("Jakob")
    userDao.insert(username)

}
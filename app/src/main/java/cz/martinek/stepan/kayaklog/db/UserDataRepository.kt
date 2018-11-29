package cz.martinek.stepan.kayaklog.db

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class UserDataRepository(private val userDao: UserDao) {


    val allUserData: LiveData<List<UserData>> =
        userDao.getAllUserData()


    @WorkerThread
    suspend fun insert(userData: UserData) {
        userDao.insert(userData)
    }


}
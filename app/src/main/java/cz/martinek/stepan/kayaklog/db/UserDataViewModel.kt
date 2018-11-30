package cz.martinek.stepan.kayaklog.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class UserDataViewModel(application: Application) : AndroidViewModel(application){

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    override fun onCleared(){
        super.onCleared()
        parentJob.cancel()
    }



    private val repository: UserDataRepository
    val allUserData: LiveData<List<UserData>>

    init{
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserDataRepository(userDao)
        allUserData = repository.allUserData
    }

    fun insert(userData: UserData) = scope.launch(Dispatchers.IO){
        repository.insert(userData)
    }

}
package cz.martinek.stepan.kayaklog

import cz.martinek.stepan.kayaklog.model.User
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


val bg = CoroutineScope(Dispatchers.Default)
val ui = CoroutineScope(Dispatchers.Main)

object Utils {
    val USERNAME = "USERNAME"

    var user: User? = null

}

object DB{
    val realm get() = Realm.getDefaultInstance()
}
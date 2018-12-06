package cz.martinek.stepan.kayaklog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


val bg = CoroutineScope(Dispatchers.Default)
val ui = CoroutineScope(Dispatchers.Main)

object Utils {
    val USERNAME = "USERNAME"

}

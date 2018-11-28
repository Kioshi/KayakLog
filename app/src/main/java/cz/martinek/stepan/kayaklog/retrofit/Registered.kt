package cz.martinek.stepan.kayaklog.retrofit

data class Registered(
    val id: Int,
    val username: String,
    val authorization: AuthToken
)
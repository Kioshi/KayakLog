package cz.martinek.stepan.kayaklog.retrofit

data class AuthToken(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val scope: String,
    var expires_in: Long
)

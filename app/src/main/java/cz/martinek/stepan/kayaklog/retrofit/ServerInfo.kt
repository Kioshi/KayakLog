package cz.martinek.stepan.kayaklog.retrofit

import android.content.Context
import android.util.Log
import cz.martinek.stepan.kayaklog.LoginActivity
import cz.martinek.stepan.kayaklog.R
import retrofit2.Response
import java.lang.Exception

object ServerInfo
{
    val ADDRESS: String = "http://10.0.2.2:8887"
    val CLIENT_ID: String = "dk.sdu.kl.android"
    val CLIENT_SECRET: String = "C3mThh9S0HNVm3hccxMKUdXLrgFBINnV"
    var authData: AuthToken? = null

    lateinit var retrofitAPI: API

    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"
    private val TOKEN_TYPE = "TOKEN_TYPE"
    private val SCOPES = "SCOPES"
    private val EXPIRATION = "EXPIRATION"

    fun updateAuthorization(context: Context, auth: AuthToken?)
    {
        if (auth != null)
        {
            auth.expires_in += (System.currentTimeMillis() / 1000L)
        }
        authData = auth
        val sharedPref = context.getSharedPreferences(context.getString(R.string.auth_preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(ACCESS_TOKEN, auth?.access_token ?: "")
        editor.putString(REFRESH_TOKEN, auth?.refresh_token ?: "")
        editor.putString(TOKEN_TYPE, auth?.token_type ?: "")
        editor.putString(SCOPES, auth?.scope ?: "")
        editor.putLong(EXPIRATION, auth?.expires_in ?: 0L)
        editor.apply()
    }

    private val APP_SCOPES = "user"
    private val GRAND_TYPE_PASSWORD = "password"
    private val GRAND_TYPE_REFRESH_TOKEN = "refresh_token"

    fun relog(context: Context): Boolean
    {
        if (authData == null)
        {
            return false
        }
        if (authData!!.expires_in > (System.currentTimeMillis()/1000L))
        {
            return true
        }

        if (!authData!!.refresh_token.isEmpty())
        {
            return refresh(context, authData!!.refresh_token)
        }
        return false
    }

    private fun refresh(context: Context, refreshToken: String): Boolean
    {
        try {
            val res = ServerInfo.retrofitAPI.refreshAccess(refreshToken, GRAND_TYPE_REFRESH_TOKEN).execute()

            updateAuthorization(context, null)
            if (!res.isSuccessful || res.body() == null)
            {
                return false
            }
        }
        catch (ex: Exception)
        {
            Log.d("Retrofit:Refresh", ex.message)
            return false
        }
        return true
    }

    fun login(context: Context, username: String? = null, password: String? = null): Boolean
    {
        lateinit var res: Response<AuthToken>
        try {

            if (username != null && password != null) {
                res = ServerInfo.retrofitAPI.login(username, password, APP_SCOPES, GRAND_TYPE_PASSWORD).execute()
            }
            else if (authData != null){
                res = ServerInfo.retrofitAPI.refreshAccess(authData!!.refresh_token, GRAND_TYPE_REFRESH_TOKEN).execute()
            }
            else{
                return false
            }
        }
        catch (ex: Exception)
        {
            Log.d("Retrofit:Login", ex.message)
            return false;
        }

        if (!res.isSuccessful || res.body() == null)
        {
            return false
        }

        updateAuthorization(context, res.body()!!)
        return true;
    }

    fun loadAuthInfo(context: Context) {
        val sp = context.getSharedPreferences(context.getString(R.string.auth_preference_file_key), Context.MODE_PRIVATE)
        val accessToken = sp.getString(ACCESS_TOKEN, null)
        val refreshToken = sp.getString(ACCESS_TOKEN, null)
        if (accessToken == null || refreshToken == null || accessToken.isEmpty())
        {
            authData = null
        }
        else
        {
            authData = AuthToken(accessToken,
                    sp.getString(TOKEN_TYPE,"")!!,
                    refreshToken,
                    sp.getString(SCOPES,"")!!,
                    sp.getLong(EXPIRATION,0))
        }


    }
}
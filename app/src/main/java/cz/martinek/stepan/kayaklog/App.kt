package cz.martinek.stepan.kayaklog

import android.app.Application
import com.google.gson.GsonBuilder
import cz.martinek.stepan.kayaklog.retrofit.API
import cz.martinek.stepan.kayaklog.retrofit.AuthInterceptor
import cz.martinek.stepan.kayaklog.retrofit.ServerInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application()
{

    override fun onCreate() {
        super.onCreate()

        // Initialize Gson for parsing JSONs
        val gson = GsonBuilder().create()
        val httpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
        {
            // enable logging for debug builds
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.addInterceptor(AuthInterceptor())

        // Initialize retrofit
        ServerInfo.retrofitAPI = Retrofit.Builder()
                .baseUrl(ServerInfo.ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create<API>(API::class.java)
    }


}
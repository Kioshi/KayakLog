package cz.martinek.stepan.kayaklog

import android.app.Application
import com.google.gson.GsonBuilder
import cz.martinek.stepan.kayaklog.retrofit.API
import cz.martinek.stepan.kayaklog.retrofit.AuthInterceptor
import cz.martinek.stepan.kayaklog.retrofit.RetrofitAPI
import cz.martinek.stepan.kayaklog.retrofit.ServerInfo
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application()
{

    override fun onCreate() {
        super.onCreate()

        // Initialize Gson for parsing JSONs
        val httpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
        {
            // enable logging for debug builds
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.addInterceptor(AuthInterceptor())

        val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val gson = gsonBuilder.create()

         API.retrofitAPI = Retrofit.Builder()
                .baseUrl(ServerInfo.ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create<RetrofitAPI>(RetrofitAPI::class.java)

        Realm.init(this)
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                        //.name("local.realm")
                        //.schemaVersion(1) // skip if you are not managing
                        .deleteRealmIfMigrationNeeded()
                        .build())
    }


}
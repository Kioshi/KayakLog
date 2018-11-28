package cz.martinek.stepan.kayaklog.retrofit

import retrofit2.Retrofit

object ServerInfo
{
    val ADDRESS: String = "http://10.0.2.2:8888"
    val CLIENT_ID: String = "dk.sdu.kl.android"
    val CLIENT_SECRET: String = "C3mThh9S0HNVm3hccxMKUdXLrgFBINnV"

    lateinit var retrofitAPI: API
}
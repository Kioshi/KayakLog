package cz.martinek.stepan.kayaklog.retrofit

import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded



interface API
{
    @FormUrlEncoded
    @POST("oauth/token")
    fun getAccessToken(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("scope") scope: String,
            @Field("grand_type") grandType: String) : Call<AuthToken>

    @POST("register")
    fun register(@Body newUser: NewUser): Call<Registered>

    @GET("me")
    fun getMe() : Call<User>

    @GET("users")
    fun getUser() : Call<User>

    @GET("users/{id}")
    fun getUser(@Path("id") id : Int) : Call<User>

    @POST("users")
    fun updateUser(@Body user: User) : Call<Unit>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id : Int): Call<Unit>

    @GET("trips")
    fun getTrips() : Call<List<Trip>>

    @GET("trips/{guid}")
    fun getTrip(@Path("guid") guid : String) : Call<Trip>

    @POST("trips/{guid}")
    fun updateTrip(@Path("guid") guid : String, @Body user: User) : Call<Unit>

    @DELETE("trips/{guid}")
    fun deleteTrip(@Path("guid") guid : String): Call<Unit>
}

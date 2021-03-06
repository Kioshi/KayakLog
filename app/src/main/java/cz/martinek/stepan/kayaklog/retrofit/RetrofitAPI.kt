package cz.martinek.stepan.kayaklog.retrofit


import cz.martinek.stepan.kayaklog.model.AcquiredAchievement
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.User
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded

interface RetrofitAPI
{
    @FormUrlEncoded
    @POST("auth/token")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("scope") scope: String,
            @Field("grant_type") grandType: String) : Call<AuthToken>

    @FormUrlEncoded
    @POST("auth/token")
    fun refreshAccess(
            @Field("refresh_token") refresh_token: String,
            @Field("grant_type") grandType: String) : Call<AuthToken>

    @POST("register")
    fun register(@Body newUser: NewUser): Call<Registered>

    @GET("me")
    fun getMe_() : Call<User>

    @GET("users")
    fun getUser_() : Call<User>

    @GET("users/{id}")
    fun getUser_(@Path("id") id : Int) : Call<User>

    @POST("users")
    fun updateUser_(@Body user: User) : Call<User>

    @POST("test")
    fun testUser_(@Body user: User) : Call<User>

    @DELETE("users/{id}")
    fun deleteUser_(@Path("id") id : Int): Call<Unit>

    @GET("trips")
    fun getTrips_() : Call<List<Trip>>

    @PUT("trips")
    fun insertTrip_(@Body user: Trip) : Call<Unit>

    @GET("trips/{guid}")
    fun getTrip_(@Path("guid") guid : String) : Call<Trip>

    @POST("trips/")
    fun updateTrip_(@Body trip: Trip) : Call<Unit>

    @DELETE("trips/{guid}")
    fun deleteTrip_(@Path("guid") guid : String): Call<Unit>

    @PUT("achievements")
    fun insertAchievement_(@Body achiev: AcquiredAchievement) : Call<Unit>
}

package cz.martinek.stepan.kayaklog.retrofit

import android.content.Context
import com.google.android.gms.tasks.Tasks.call
import cz.martinek.stepan.kayaklog.model.AcquiredAchievement
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.User
import retrofit2.Response

private enum class Requests{
    GET_ME,
    GET_USERS,
    GET_USER_ID,
    POST_USER,
    DELETE_USER,
    GET_TRIPS,
    GET_TRIP,
    POST_TRIP,
    DELETE_TRIP
}


object API
{
    lateinit var retrofitAPI: RetrofitAPI

/*
    private fun <T,A>call(context: Context, type: Requests, arg: A? = null, arg2: Trip? = null) : T?
    {
        val res: Response<T>

        ServerInfo.relog(context)
        when(type)
        {
            Requests.GET_ME -> res = retrofitAPI.getMe_().execute() as Response<T>
            Requests.GET_USERS -> res = retrofitAPI.getUser_().execute() as Response<T>
            Requests.GET_USER_ID -> res = retrofitAPI.getUser_(arg!! as Int) as Response<T>
            Requests.POST_USER -> res = retrofitAPI.updateUser_(arg!! as User) as Response<T>
            Requests.DELETE_USER -> res = retrofitAPI.deleteUser_(arg!! as Int) as Response<T>
            Requests.GET_TRIPS -> res = retrofitAPI.getTrips_() as Response<T>
            Requests.GET_TRIP -> res = retrofitAPI.getTrip_(arg!! as String) as Response<T>
            Requests.POST_TRIP -> res = retrofitAPI.updateTrip_(arg!! as String, arg2!!) as Response<T>
            Requests.DELETE_TRIP -> res = retrofitAPI.deleteTrip_(arg!! as String) as Response<T>
        }

        if (!res.isSuccessful)
        {
            when(res.code() / 100)
            {
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }

        return res.body()
    }

    fun getMe(context: Context) = call<User, Unit>(context, Requests.GET_ME);
    fun getUser(context: Context) = call<User, Unit>(context, Requests.GET_USERS);
    fun getUser(context: Context, id: Int) = call<User, Int>(context, Requests.GET_USER_ID, id);
    fun updateUser(context: Context, user: User) = call<User, User>(context, Requests.POST_USER, user);
    fun deleteUser(context: Context, id: Int) = call<Unit, Int>(context, Requests.DELETE_USER, id);
    fun updateTrip(context: Context, guid: String, trip: Trip) = call<Unit, String>(context, Requests.POST_TRIP, guid, trip);
    fun deleteTrip(context: Context, guid: String) = call<Unit, String>(context, Requests.DELETE_TRIP, guid);
    fun getTrips(context: Context) = call<List<Trip>, Unit>(context, Requests.GET_TRIPS);
    */

    fun getUser(context: Context): User?
    {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.getUser_().execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }
    fun updateUser(context: Context, user: User): User?
    {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.updateUser_(user).execute()


        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }
    fun getTrip(context: Context, guid: String): Trip?
    {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.getTrip_(guid).execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }
    fun insertTrip(context: Context, trip: Trip): Unit?
    {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.insertTrip_(trip).execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }

    fun insertAchievement(context: Context, achiev: AcquiredAchievement): Unit?
    {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.insertAchievement_(achiev).execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not suppose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }

    fun updateTrip(context: Context, trip: Trip): Unit? {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.updateTrip_(trip).execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }

    fun deleteTrip(context: Context, guid: String): Unit? {
        ServerInfo.relog(context)

        val res =  API.retrofitAPI.deleteTrip_(guid).execute()

        if (res.isSuccessful)
        {
            when(res.code() / 100)
            {
                2 -> {}
                //Not supose to happen
                3 -> throw RedirectException(res.code(), res.message())
                4 -> throw UnauthenticatedException(res.code(), res.message())
                5 -> throw  ServerErrorException(res.code(), res.message())
                else -> throw ServerErrorException(res.code(), res.message())
            }
        }
        return res.body();
    }
}

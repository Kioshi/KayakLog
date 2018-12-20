package cz.martinek.stepan.kayaklog

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.gson.GsonBuilder
import cz.martinek.stepan.kayaklog.model.AcquiredAchievement
import cz.martinek.stepan.kayaklog.model.Path
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.User
import cz.martinek.stepan.kayaklog.retrofit.RetrofitAPI
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataBindingTest {

    lateinit var retrofit: RetrofitAPI;
    lateinit var realm: Realm

    @Before
    fun setUp() {
        val httpClientBuilder = OkHttpClient.Builder()
        val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val gson = gsonBuilder.create()

        retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create<RetrofitAPI>(RetrofitAPI::class.java)


        Realm.init(InstrumentationRegistry.getTargetContext())
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                        .name("test.realm")
                        //.schemaVersion(1) // skip if you are not managing
                        .deleteRealmIfMigrationNeeded()
                        .build())

        realm = Realm.getDefaultInstance()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("cz.martinek.stepan.kayaklog", appContext.packageName)

        realm.beginTransaction()
        realm.deleteAll()
        val user = realm.createObject(User::class.java, "tester")

        val paths = mutableListOf<Path>()
        val p1 = Path()
        p1.pos = 0
        p1.lat = 0.1
        p1.long = -0.1
        paths.add(p1)
        val p2 = Path()
        p2.pos = 1
        p2.lat = 0.3
        p2.long = -0.2
        paths.add(p2)

        val trips = mutableListOf<Trip>();
        val trip = realm.createObject(Trip::class.java, UUID.randomUUID().toString())
            trip.name = "trip1"
        trip.description = "desct trip1"
        trip.timeCreated = Calendar.getInstance().time
        trip.duration = 123
        trip.publiclyAvailable = true
        trip.path!!.addAll(paths.map { DB.realm.copyToRealm(it) })

        trips.add(trip)

        user.trips!!.addAll(trips)

        val achievs = mutableListOf<AcquiredAchievement>();
        val achiev1 = realm.createObject(AcquiredAchievement::class.java, UUID.randomUUID().toString())
        achiev1.achievementId = 1
        achiev1.acquiredTime = Calendar.getInstance().time
        achiev1.extraInfo = "test"
        achievs.add(achiev1)

        val achiev2 = realm.createObject(AcquiredAchievement::class.java, UUID.randomUUID().toString())
        achiev2.achievementId = 3
        achiev2.acquiredTime = Calendar.getInstance().time
        achiev2.extraInfo = "test2"
        achievs.add(achiev2)

        user.achievements!!.addAll(achievs)
        realm.commitTransaction()


        val originalUser = realm.copyFromRealm(user)
        val recievedUser = retrofit.testUser_(originalUser).execute().body()!!

        assertEquals(originalUser.username, recievedUser.username)
        assertEquals(originalUser.trips!!.size, recievedUser.trips!!.size)
        assertEquals(originalUser.achievements!!.size, recievedUser.achievements!!.size)

        for (i in 0..originalUser.trips!!.size-1)
        {
            val ot = originalUser.trips!![i]!!
            val rt = recievedUser.trips!![i]!!
            assertEquals(ot.guid, rt.guid)
            assertEquals(ot.name, rt.name)
            assertEquals(ot.description, rt.description)
            assertEquals(ot.timeCreated.toString(), rt.timeCreated.toString())
            assertEquals(ot.duration, rt.duration)
            assertEquals(ot.publiclyAvailable, rt.publiclyAvailable)
            assertEquals(ot.path!!.size, rt.path!!.size)

            for (j in 0..ot.path!!.size-1) {
                val op = ot.path!![j]!!
                val rp = rt.path!![j]!!

                assertEquals(op.pos, rp.pos)
                assertEquals(op.lat, rp.lat, 1E-5)
                assertEquals(op.long, rp.long, 1E-5)
            }
        }
        for (i in 0..originalUser.achievements!!.size-1)
        {
            val oa = originalUser.achievements!![i]!!
            val ra = recievedUser.achievements!![i]!!

            assertEquals(oa.achievementId, ra.achievementId)
            assertEquals(oa.guid, ra.guid)
            assertEquals(oa.extraInfo, ra.extraInfo)
            assertEquals(oa.acquiredTime.toString(), ra.acquiredTime.toString())
        }

    }

    @After
    fun tearDown() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }
}

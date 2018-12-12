package cz.martinek.stepan.kayaklog

//import cz.martinek.stepan.kayaklog.database.DBHelper
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import cz.martinek.stepan.kayaklog.model.AcquiredAchievement
import cz.martinek.stepan.kayaklog.model.Path
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.retrofit.API
import kotlinx.android.synthetic.main.activity_trip.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import java.io.Serializable
import java.lang.Exception
import java.util.*

class TripActivity : AppCompatActivity(), LocationListener, Serializable {
    //Trip path
    private var path: ArrayList<Path> = ArrayList()

    private var map: GoogleMap? = null
    private var line: Polyline? = null

    private var duration: Int = 0
    private var running: Boolean = false
    private var speed: Float = 0.0f
    private var distance: Float = 0.0f

    private lateinit var counter: Job


    //Used for user permission
    companion object {
        private const val LOCATION_PERMISSION_REQUESTED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        counter = ui.launch {
            while(true) {
                if (running) {
                    duration++;
                    durationTV.text = "Duration: $duration"
                }
                delay(1000)
            }
        }

        // request map and set it up asynchronously
        (mapFragment as SupportMapFragment).getMapAsync{
            map = it
            setUpMap(map!!)
        }
    }

    @SuppressLint("NewApi")
    fun startStopButtonClick(view: View)
    {
        if (!running) {
            start()
        }
        else
        {
            running = false
            alert("Are you sure you want to cancel current trip?") {
                positiveButton("Yes")  { cancel(); it.dismiss() }
                negativeButton("No")  { running = true; it.dismiss() }
                onCancelled { running = true }
            }.show()

        }
    }

    fun start()
    {
        //Checking for permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), TripActivity.LOCATION_PERMISSION_REQUESTED)
            return
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1f, this)
        running = true
        startStopButt.text = "Cancel"
        saveTripButt.isEnabled = true
    }

    fun cancel()
    {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(this)

        durationTV.text = "Duration: N/A"
        speedTV.text = "Speed: N/A"
        distanceTV.text = "Distance: N/A"
        running = false
        duration = 0
        speed = 0.0f
        distance = 0.0f
        path.clear()
        line?.remove()
        line = null
        saveTripButt.isEnabled = false

        startStopButt.text = "Start"
    }

    fun save(view: View)
    {
        running = false
        checkAchievements()
        alert("Are you sure you want to cancel current trip?") {
            titleResource = R.string.saveTripTitle
            lateinit var name: EditText
            lateinit var desc: EditText
            lateinit var public: Switch
            customView{
                verticalLayout {
                    padding = dip(16)
                        name  = editText {
                        hintResource = R.string.nameOfTrip
                        setText("Trip: ${Calendar.getInstance().time}")
                    }
                        desc  = editText {
                        hintResource = R.string.descriptionOfTrip
                    }
                        public = switch{
                        textResource = R.string.isTripPublic
                    }

                }
            }
            positiveButton("Save")  {
                saveTrip(name.text.toString(), desc.text.toString(), public.isChecked)
                cancel();
                it.dismiss()
            }
            negativeButton("Continue")  { running = true; it.dismiss() }
            onCancelled { running = true }
        }.show()
    }

    fun saveTrip(name: String, desc: String, public: Boolean)
    {
        val context = this
        ui.launch {
            DB.realm.beginTransaction()
            val trip = DB.realm.createObject(Trip::class.java, UUID.randomUUID().toString())
            trip.name = name
            trip.description = desc
            trip.publiclyAvailable = public
            trip.duration = duration
            trip.timeCreated = Calendar.getInstance().time
            trip.path!!.addAll(path.map { DB.realm.copyToRealm(it) })
            Utils.user?.trips?.add(trip)
            DB.realm.commitTransaction()
            Toast.makeText(context, "Trip was saved...", Toast.LENGTH_LONG).show()
            val copy = DB.realm.copyFromRealm(trip)
            bg.async {
                try {
                    API.insertTrip(context, copy)
                }
                catch (ex: Exception)
                {
                    Log.d("Retrofit:InsertTrip", ex.message)
                }
            }.await()
        }
    }

    // LocationListener call backs
    override fun onLocationChanged(location: Location)
    {
        if (!running)
            return

        speed = location.speed


        var latLng = LatLng(location.latitude, location.longitude)
        if (path.size >= 4) {
            var lat = latLng.latitude
            var lon = latLng.longitude
            for (i in (path.size - 4)..(path.size - 1)) {
                lat += path[i].lat
                lon += path[i].long
            }
            latLng = LatLng(lat / 5.0, lon/5.0)
        }

        val p = Path()
        p.pos = path.size
        p.lat = latLng.latitude
        p.long = latLng.longitude

        path.add(p)

        if (path.size > 1)
        {
            val lastLoc = Location("")
            lastLoc.latitude = path[path.size-2].lat
            lastLoc.longitude = path[path.size-2].long
            val thisLoc = Location("")
            thisLoc.latitude = path[path.size-1].lat
            thisLoc.longitude = path[path.size-1].long
            distance += thisLoc.distanceTo(lastLoc)
        }

        // Update polyline
        if (line == null)
        {
            line = map?.addPolyline(PolylineOptions().width(5f).color(Color.RED).addAll(path.map { LatLng(it.lat, it.long) }))
        }
        else
        {
            line!!.points = path.map { LatLng(it.lat, it.long) }
        }

        //Move map
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))

        // Update textViews
        speedTV.text = "Speed: %.2f".format(speed)
        distanceTV.text = "Distance: %.2f".format(distance)

        checkAchievements()
    }

    private fun checkAchievements() {

        if (Utils.user == null)
            return

        Achievements.speed.forEach { s ->
            if (speed > s.second && Utils.user?.achievements!!.none { it.achievementId == s.first })
                rewardAchievement(s.first)
        }

        Achievements.distance.forEach { d ->
            if (distance > d.second && Utils.user?.achievements!!.none { it.achievementId == d.first })
                rewardAchievement(d.first)
        }

        Achievements.duration.forEach { d ->
            if (duration > d.second && Utils.user?.achievements!!.none { it.achievementId == d.first })
                rewardAchievement(d.first)
        }
    }

    fun rewardAchievement(id: Int) {
        lateinit var name: String
        when (id) {
            0 -> name = "Speed over 10m/s"
            1 -> name = "Speed over 20m/s"
            2 -> name = "Speed over 30m/s"
            3 -> name = "Distance over 5km"
            4 -> name = "Distance over 15km"
            5 -> name = "Distance over 30km"
            6 -> name = "Duration over 1h"
            7 -> name = "Duration over 2h"
            8 -> name = "Duration over 3h"
        }


        val context = this
        ui.launch {
            DB.realm.beginTransaction()
            val achiev = DB.realm.createObject(AcquiredAchievement::class.java, UUID.randomUUID().toString())
            achiev.achievementId = id
            achiev.acquiredTime = Calendar.getInstance().time
            achiev.extraInfo = name
            Utils.user?.achievements?.add(achiev)
            DB.realm.commitTransaction()
            val copy = DB.realm.copyFromRealm(achiev)
            Toast.makeText(context, "Congratulation! You were awarded with new achievement '$name'.", Toast.LENGTH_LONG).show()

            bg.async {
                try {
                API.insertAchievement(context, copy)
                } catch (ex: Exception) {
                    Log.d("Retrofit:InsertAchiev", ex.message)
                }
            }.await()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

    private fun setUpMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
    }

    override fun onBackPressed() {
        if (!running)
        {
            super.onBackPressed()
            return
        }

        running = false
        alert("Are you sure you want to cancel current trip?") {
            positiveButton("Yes")  { cancel(); it.dismiss(); super.onBackPressed() }
            negativeButton("No")  { running = true; it.dismiss() }
            onCancelled { running = true }
        }.show()
    }
}


package cz.martinek.stepan.kayaklog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import cz.martinek.stepan.kayaklog.model.Path
import kotlinx.android.synthetic.main.activity_trip.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.io.Serializable
import java.util.*

class TripActivity : AppCompatActivity(), LocationListener, Serializable {

    private var tripID: Int = 1
    private var lat: Double = 0.0
    private var long: Double = 0.0

    //Trip path
    private var path: ArrayList<Path> = ArrayList()

    private var map: GoogleMap? = null
    private var line: Polyline? = null

    private var duration: Int = 0
    private var running: Boolean = false
    private var speed: Float = 0.0f
    private var distance: Float = 0.0f

    private var counter = ui.launch {
        while(true) {
            if (running) {
                duration++;
                durationTV.text = "Duration: $duration"
            }
            delay(1000)
        }
    }


    //Used for user permission
    companion object {
        private const val LOCATION_PERMISSION_REQUESTED = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)
        // request map and set it up asynchronously
        (mapFragment as SupportMapFragment).getMapAsync{
            map = it
            setUpMap(map!!)
        }
    }

    @SuppressLint("NewApi")
    fun startButtonClick(view: View)
    {
        if (running) {
            start()
        }
        else
        {
            alert("Are you sure you want to cancel current trip?") {
                yesButton { cancel() }
                noButton {}
            }.show()

        }
        //Getting current date and time
        //val current = LocalDateTime.now()
        //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        //timeCreated = current.format(formatter)
        //testTrip.setText(timeCreated)

    }

    fun start()
    {
        //Checking for permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), TripActivity.LOCATION_PERMISSION_REQUESTED)
            return
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        running = true
        startStopButt.text = "Cancel"
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

        startStopButt.text = "Start"
    }

    fun save(view: View)
    {
        val intent = Intent(this, LogTripActivity::class.java).apply {
            putExtra("duration", duration)
            putExtra("path", path)
            //putExtra("achievements", achievements)
        }
        startActivity(intent)
    }

    data class TempPath(
     val time: Date,
     val loc: Location
    )

    var tempPath = ArrayList<TempPath>()

    // LocationListener call backs
    override fun onLocationChanged(location: Location)
    {
        tempPath.add(TempPath(Calendar.getInstance().time, location))
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

        path.add(Path(path.size,latLng.latitude, latLng.longitude))

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
        speedTV.text = "Speed: $speed"
        distanceTV.text = "Distance: $distance"
        debugInfoTv.setText(path.toString())
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

    private fun setUpMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

    }
}
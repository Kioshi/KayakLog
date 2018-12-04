package cz.martinek.stepan.kayaklog

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_trip.*

class TripActivity : AppCompatActivity(), LocationListener, GoogleMap.OnMarkerClickListener {

    private var lat: Double = 0.0
    private var long: Double = 0.0

    private var trip: ArrayList<LatLng> = ArrayList()

    private var map: GoogleMap? = null
    private var line: Polyline? = null

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

    fun startButtonClick(view: View)
    {
        //Checking for permission
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),TripActivity.LOCATION_PERMISSION_REQUESTED)
            return
        }
        
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
    }

    // LocationListener call backs
    override fun onLocationChanged(location: Location) {
        //Our current location
        val ourCurrentPosition = LatLng(location.latitude, location.longitude)

        onChange(ourCurrentPosition)

        map?.addMarker(MarkerOptions().position(ourCurrentPosition).title("Our current location"))
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(ourCurrentPosition, 18f))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

    // Google map callbacks
    override fun onMarkerClick(marker: Marker?) = false


    // Custom functions
    private fun onChange(latLng: LatLng){
        lat = latLng.latitude
        long = latLng.longitude
        trip.add(latLng)

        testTrip.setText(trip.size.toString())
        line = map?.addPolyline(PolylineOptions().addAll(trip).width(5f).color(Color.RED))
    }

    private fun setUpMap(map: GoogleMap) {
        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        //val ourPosition = LatLng(lat, long)
        //map.addMarker(MarkerOptions().position(ourPosition).title("Our current location"))
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(ourPosition, 18f))

        //Map type options
        //line = map.addPolyline(PolylineOptions().addAll(trip).width(5f).color(Color.RED))
    }


}
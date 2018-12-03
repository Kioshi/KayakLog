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
import kotlinx.android.synthetic.main.activity_trip.*

class TripActivity : AppCompatActivity(), LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false

    private var x: Double = 0.0
    private var y: Double = 0.0


    private lateinit var trip: ArrayList<LatLng>

    private lateinit var map: GoogleMap
    private lateinit var line: Polyline

    //Used for user permission
    companion object {
        private const val LOCATION_PERMISSION_REQUESTED = 1
    }

    override fun onLocationChanged(location: Location) {
        //x = location.latitude
        //y = location.longitude

        //Our current location
        val ourCurrentPosition = LatLng(location.latitude, location.longitude)

        onChange(ourCurrentPosition)

        map.addMarker(MarkerOptions().position(ourCurrentPosition).title("Our current location"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ourCurrentPosition, 18f))


    }

    private fun onChange(latLng: LatLng){
        val x = latLng.latitude
        val y = latLng.longitude

        trip = ArrayList<LatLng>()
        trip.add(LatLng(x, y))

        val text = findViewById<TextView>(R.id.testTrip)
        text.setText(trip.size.toString())


    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        //map.isMyLocationEnabled = true
        map = googleMap
        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)
        //Setting up the map
        setUpMap()
    }

    private fun setUpMap() {
        //Checking for permission
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                TripActivity.LOCATION_PERMISSION_REQUESTED
            )
            return
        }
        //val ourPosition = LatLng(x, y)
        //map.addMarker(MarkerOptions().position(ourPosition).title("Our current location"))
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(ourPosition, 18f))
        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)

        //Map type options
        map.mapType = GoogleMap.MAP_TYPE_HYBRID

        //line = map.addPolyline(PolylineOptions().addAll(trip).width(5f).color(Color.RED))

    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
        }

    }
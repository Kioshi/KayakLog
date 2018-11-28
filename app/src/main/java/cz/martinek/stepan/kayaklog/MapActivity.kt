package cz.martinek.stepan.kayaklog

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var map: GoogleMap
    private lateinit var line: Polyline
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    //Used for user permission
    companion object {
        private const val LOCATION_PERMISSION_REQUESTED = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location -> if (location != null) {
            lastLocation = location
            val ourCurrentLocation = LatLng(location.latitude, location.longitude)
        }
        }
        map = googleMap
        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)
        //Setting up the map
        setUpMap()
    }

    override fun onPause(){
        super.onPause()
    }

    override fun onResume(){
        super.onResume()
    }

    private fun setUpMap() {
        //Checking for permission
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUESTED)
            return
        }

        //Our last known location from TripActivity
        val x = intent.getStringExtra("Lat")
        val y = intent.getStringExtra("Long")
        val ourPosition = LatLng(x.toDouble(),y.toDouble())
        val nexPosition = LatLng(x.toDouble() - 0.2, y.toDouble() - 0.2)

        var mapPoints: ArrayList<LatLng>
        mapPoints = ArrayList<LatLng>()
        mapPoints.add(ourPosition)
        mapPoints.add(nexPosition)

        map.addMarker(MarkerOptions().position(ourPosition).title("Our current location"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ourPosition, 18f))
        //Map type options
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        //Test of polyline
        line = map.addPolyline(PolylineOptions().addAll(mapPoints).width(5f).color(Color.RED))

    }

    private fun onUpdatePosition() {


    }


}

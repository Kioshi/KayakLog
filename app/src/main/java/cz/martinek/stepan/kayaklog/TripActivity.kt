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
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_trip.*

class TripActivity : AppCompatActivity(), LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false


    private var x: Double = 0.0
    private var y: Double = 0.0

    private lateinit var map: GoogleMap
    private lateinit var line: Polyline

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {

        }

    }

    override fun onLocationChanged(location: Location) {
        x = location.latitude
        y = location.longitude


    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}





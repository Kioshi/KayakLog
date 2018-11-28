package cz.martinek.stepan.kayaklog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
*/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val x = intent.getStringExtra("Lat")
        val y = intent.getStringExtra("Long")

        map = googleMap
        
        //My current position
        val ourPosition = LatLng(x.toDouble(),y.toDouble())

        map.addMarker(MarkerOptions().position(ourPosition).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(ourPosition))

        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)

    }
}

package cz.martinek.stepan.kayaklog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_trip.*

class TripActivity : AppCompatActivity(), LocationListener, GoogleMap.OnMarkerClickListener {


    private lateinit var map: GoogleMap

    override fun onLocationChanged(location: Location) {


        val x = location.latitude
        val y = location.longitude

        //val myPlace = LatLng(x, y)

        GPSText.setText("\nYour current location: (" + x + ":" + y + ")")


    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onProviderEnabled(provider: String) {
        //GPSText.setText("\nProvider Enabled")

        GPSText.append("\nProvider Enabled")
    }

    override fun onProviderDisabled(provider: String) {
        //GPSText.setText("\nProvider Disabled")

        GPSText.append("\nProvider Disabled")
    }


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync{
            val x = 0.0//intent.getStringExtra("Lat").toDouble()
            val y = 0.0//intent.getStringExtra("Long").toDouble()

            map = it

            //My current position
            val ourPosition = LatLng(x,y.toDouble())

            map.addMarker(MarkerOptions().position(ourPosition).title("Marker in Sydney"))
            map.moveCamera(CameraUpdateFactory.newLatLng(ourPosition))

            map.getUiSettings().setZoomControlsEnabled(true)
            map.setOnMarkerClickListener(this)

        }



    //val GPSText: TextView = findViewById(R.id.GPSText) as TextView


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //val startbtn = findViewById<Button>(R.id.StartTripBtn)


        startButton.setOnClickListener {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)

        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

}





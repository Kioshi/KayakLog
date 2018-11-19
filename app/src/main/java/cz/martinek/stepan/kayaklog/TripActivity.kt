package cz.martinek.stepan.kayaklog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.TextView

class TripActivity : AppCompatActivity() {


   @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

       val GPSText : TextView = findViewById(R.id.GPSText) as TextView


       val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.

            val loc = location.toString()
                val GPSText : TextView = findViewById(R.id.GPSText) as TextView

                GPSText.append("\n " + loc)


            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
                GPSText.append("\nProvider Enabled")

            }

            override fun onProviderDisabled(provider: String) {
                GPSText.append("\nProvider Disabled")
            }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)

        }
    }


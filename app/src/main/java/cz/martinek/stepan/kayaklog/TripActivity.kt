package cz.martinek.stepan.kayaklog

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TripActivity : AppCompatActivity() {


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        val GPSText: TextView = findViewById(R.id.GPSText) as TextView


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val startbtn = findViewById<Button>(R.id.StartTripBtn)

        startbtn.setOnClickListener {

            val locationListener = object : LocationListener {

                override fun onLocationChanged(location: Location) {


                    val x = location.latitude
                    val y = location.longitude

                    GPSText.setText("\nYour current location: (" + x + ":" + y + ")")


                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                }

                override fun onProviderEnabled(provider: String) {
                    //GPSText.setText("\nProvider Enabled")

                    //GPSText.append("\nProvider Enabled")
                }

                override fun onProviderDisabled(provider: String) {
                    //GPSText.setText("\nProvider Disabled")

                    //GPSText.append("\nProvider Disabled")
                }
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

        }
    }
}



package cz.martinek.stepan.kayaklog

import android.arch.lifecycle.ViewModelProviders
import android.location.Location
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_trip.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.CompletableFuture.runAsync

class TripActivity : AppCompatActivity(), LocationListener {



    override fun onLocationChanged(location: Location) {


        val x = location.latitude
        val y = location.longitude

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


    //@SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)
/*
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)

        userDataViewModel.allUserData.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })
*/



        // Logic on DB
        val context = this
        doAsync {
           // val users = AppDatabase.getInstance(context).getUserData().getAll
            GPSText.append("\nDB Working")

            // Db work with UI
            uiThread {
                Toast.makeText(context,"Yay!",Toast.LENGTH_LONG).show()
            }
        }

            GPSText.append("\nDB Working")
        //val GPSText: TextView = findViewById(R.id.GPSText) as TextView


        //val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //val startbtn = findViewById<Button>(R.id.StartTripBtn)

        //startButton.setOnClickListener {

            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)

        //}
    }
}



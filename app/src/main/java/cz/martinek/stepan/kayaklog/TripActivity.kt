package cz.martinek.stepan.kayaklog

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import cz.martinek.stepan.kayaklog.model.DbWorkerThread
import cz.martinek.stepan.kayaklog.model.RoomDB
import kotlinx.android.synthetic.main.activity_trip.*

class TripActivity : AppCompatActivity(), LocationListener {

    private var mDb: RoomDB? = null

    private lateinit var mDbWorkerThread: DbWorkerThread


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


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")

        mDbWorkerThread.start()

            mDb = RoomDB.getInstance(this)

            GPSText.append("\nDB Working")
        //val GPSText: TextView = findViewById(R.id.GPSText) as TextView


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //val startbtn = findViewById<Button>(R.id.StartTripBtn)

        startButton.setOnClickListener {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)

        }
    }
}



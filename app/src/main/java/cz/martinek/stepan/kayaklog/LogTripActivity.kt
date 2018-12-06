package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import cz.martinek.stepan.kayaklog.database.DBHelper
import cz.martinek.stepan.kayaklog.database.Trip
import cz.martinek.stepan.kayaklog.model.Path
import kotlinx.android.synthetic.main.activity_log_trip.*
import java.util.*

class LogTripActivity : AppCompatActivity() {

    val dbHandler = DBHelper(this, null,null,1)

    private var tripName: String = ""
    private var tripDescription: String = ""
    private var publicTrip: Boolean = false

    private val id = 0
    private val test = 0
    private val date = Date()

    private var tripPath: List<LatLng> = ArrayList()
    private var path: ArrayList<Path> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_trip)



        val tripId: Int = intent.getIntExtra("TripID", id)
        val tripDuration: Int = intent.getIntExtra("TripDuration", test)
        tripPath = intent.getSerializableExtra("TripPath") as ArrayList<LatLng>

        convertPath()

        testLog.setText(tripId.toString() + tripDuration.toString() + tripPath.toString())

        //Is the trip public or private
        switchPublicTrip.setOnCheckedChangeListener { buttonView, isChecked ->
            publicTrip = isChecked
        }
    }

    fun convertPath(){

        for (i in tripPath.indices){
            val p = tripPath[i]
            val lat = p.latitude
            val long = p.longitude

            var newPath = Path(path.size, lat, long)
            path.add(newPath)
        }
    }

    //Sending name, description and boolean about public trip back to the TripActivity
    fun onClickSubmitLogTrip(view: View){

        tripName = findViewById<TextView>(R.id.logTripName).toString()
        tripDescription = findViewById<TextView>(R.id.logTripDescription).toString()
        //Database call
        dbHandler.addTrip(Trip("", date, 0,path))

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

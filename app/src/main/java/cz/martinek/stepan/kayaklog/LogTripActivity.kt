package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_log_trip.*

class LogTripActivity : AppCompatActivity() {

    private var tripName: String = ""
    private var tripDescription: String = ""
    private var publicTrip: Boolean = false

    private val id = 0
    private val test = 0

    private var tripPath: ArrayList<LatLng> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_trip)

        val tripId: Int = intent.getIntExtra("TripID", id)
        val tripDuration: Int = intent.getIntExtra("TripDuration", test)
        tripPath = intent.getSerializableExtra("TripPath") as ArrayList<LatLng>

        testLog.setText(tripId.toString() + tripDuration.toString() + tripPath.toString())

        //Is the trip public or private
        switchPublicTrip.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                publicTrip = true

            } else {
                publicTrip = false
            }
        }
    }
    //Sending name, description and boolean about public trip back to the TripActivity
    fun onClickSubmitLogTrip(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_log_trip.*

class LogTripActivity : AppCompatActivity() {

    private var publicTrip: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_trip)

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
        val intent = Intent(this, TripActivity::class.java).apply {
            putExtra("TripName", logTripName.text.toString())
            putExtra("TripDescription", logTripDescription.text.toString())
            putExtra("PublicTrip", publicTrip)
        }
        startActivity(intent)
    }
}

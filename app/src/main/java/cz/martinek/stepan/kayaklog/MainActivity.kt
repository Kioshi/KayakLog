package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()


        if (Utils.user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        userNameView.text = Utils.user!!.username
    }

    //Intent
    fun tripAction(view: View){
        startActivity(Intent(this, TripActivity::class.java))
    }

    fun logAction(view: View){
        startActivity(Intent(this, TripsActivity::class.java))
    }

    fun achievementAction(view: View){
        startActivity(Intent(this, AchievementsActivity::class.java))
    }

    fun search(view: View)
    {
        val intent = Intent(this, ShowTripActivity::class.java)
        intent.putExtra("GUID", guidET.text.toString())
        intent.putExtra("LOCAL", false)
        startActivity(intent)
    }
}

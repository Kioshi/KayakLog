package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tripButton = findViewById<Button>(R.id.startTripButton)
        tripButton.setOnClickListener{
            tripAction()
        }
        val logButton = findViewById<Button>(R.id.logButton)
        logButton.setOnClickListener{
            logAction()
        }
        val achievementButton = findViewById<Button>(R.id.achievementsButton)
        achievementButton.setOnClickListener{
            achievementAction()
        }

    }
    //Intent
    private fun tripAction(){
        val intent = Intent(this, TripActivity::class.java)
        startActivity(intent)
    }

    private fun logAction(){
        val intent = Intent(this, LogActivity::class.java)
        startActivity(intent)
    }

    private fun achievementAction(){
        val intent = Intent(this, AchievementActivity::class.java)
        startActivity(intent)
    }
}

package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import cz.martinek.stepan.kayaklog.database.DBHelper
import cz.martinek.stepan.kayaklog.database.Trip
import cz.martinek.stepan.kayaklog.database.User
import cz.martinek.stepan.kayaklog.model.Path
import java.util.*

class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Trip name
         val name: String = "tur til netto"
        //Trip description
         val description: String = ""
        //Trip values
         val lat = 0.0
         val long = 0.0
         val public = true
         val duration = 1

         val timeCreated = Date()

        var p: Path? = null

        p = Path(1.1,1.2)


        //Trip path
        var path: ArrayList<Path> = ArrayList()

        path.add(p)

        var trip: Trip? = null

        trip = Trip(description, name, public, duration, timeCreated, path)

        val dbHandler = DBHelper(this, null, null, 1)

        dbHandler.addTrip(trip)

        findViewById<TextView>(R.id.userNameView).apply {
            text = dbHandler.getTrip(1)
        }


        val tripButton = findViewById<Button>(R.id.startTripButton)
        tripButton.setOnClickListener{
            tripAction()
        }
        val logButton = findViewById<Button>(R.id.logButton)
        logButton.setOnClickListener{
            //newProduct()
            //lookupProduct()
            logAction()
        }
        val achievementButton = findViewById<Button>(R.id.achievementsButton)
        achievementButton.setOnClickListener{
            achievementAction()
        }



/*
        //Getting data from Signup activity
        val userName = intent.getStringExtra("userName")
        findViewById<TextView>(R.id.userNameView).apply {
            text = userName
        }
        val userPassword = intent.getStringExtra("userPassword")
        val userEmail = intent.getStringExtra("userEmail")
*/
    }

    fun newUser(username: String){

        val dbHandler = DBHelper(this, null, null, 1)


        val user = User(username)



        dbHandler.addUser(user)
    }

    fun lookupUser(){
        val dbHandler = DBHelper(this, null, null, 1)



        val user = dbHandler.findUser("peter")

        if(user != null){

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

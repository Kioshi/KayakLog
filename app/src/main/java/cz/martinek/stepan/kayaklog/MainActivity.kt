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
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //var tripID: Int = 1
        //Trip name
         var name: String = "tur til netto"
        //Trip description
         var description: String = "Jeg OKSFOÆEDF gik en tur til netto. Glemte kayaken"
        //Trip values
         var lat: Double = 55.0
         var long: Double = 17.0
         var public: Boolean = true
         val duration = 1

         val timeCreated: Date = Date()

        var p: Path? = null

        p = Path(1.1,1.2)


        //Trip path
        var path: ArrayList<Path> = ArrayList()

        path.add(p)

        var trip: Trip? = null

        trip = Trip(name, description, public, duration, timeCreated, path)

        val dbHandler = DBHelper(this, null, null, 1)






        dbHandler.addTrip(trip)

        //newUser("hitler")


        //val dbHandler = DBHelper(this, null, null, 1)




        findViewById<TextView>(R.id.userNameView).apply {
            //text = dbHandler.findUser("hitler")!!.username.toString()

            text = dbHandler.getTrip(1)

            //text = dbHandler.checkTable().toString()
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

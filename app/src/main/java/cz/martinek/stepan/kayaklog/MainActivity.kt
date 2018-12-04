package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import cz.martinek.stepan.kayaklog.database.DBHelper
import cz.martinek.stepan.kayaklog.database.User

class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //newProduct("snes")
        newUser("leon")


        val dbHandler = DBHelper(this, null, null, 1)



        //val product = dbHandler.findProduct("Playstation")

        findViewById<TextView>(R.id.userNameView).apply {
            text = dbHandler.findUser("leon")!!.username.toString()
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


        //val user = User(user)



        //dbHandler.addUser(user)
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

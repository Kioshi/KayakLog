package cz.martinek.stepan.kayaklog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import cz.martinek.stepan.kayaklog.R.id.text
import cz.martinek.stepan.kayaklog.database.DBHelper
import cz.martinek.stepan.kayaklog.database.Product
import cz.martinek.stepan.kayaklog.db.AppDatabase

class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newProduct()

        val dbHandler = DBHelper(this, null, null, 1)



        //val product = dbHandler.findProduct("Playstation")

        findViewById<TextView>(R.id.userNameView).apply {
            text = dbHandler.findProduct("Playstation")!!.productName.toString()
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


    fun newProduct(){

        val dbHandler = DBHelper(this, null, null, 1)
/*
        val quantity = Integer.parseInt(productQuantity.text.toString())

        val product = Product(productName.text.toString(), quantity)
*/

        val quantity = 1

        val product = Product("Playstation", quantity)

        dbHandler.addProduct(product)
        //productName.setText("")


    }


    fun lookupProduct(){
        val dbHandler = DBHelper(this, null, null, 1)



        val product = dbHandler.findProduct("Playstation")

        if(product != null){

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

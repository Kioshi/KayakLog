package cz.martinek.stepan.kayaklog

//import cz.martinek.stepan.kayaklog.database.DBHelper
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class AchievementActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)

        //val dbHandler = DBHelper(this, null,null,1)
        //val tripCursor = dbHandler.getTrip(1)


        val test = findViewById<TextView>(R.id.testAchievement)
        //test.setText(tripCursor.toString())

        //var col = tripCursor.columnNames

        //val t = col.joinToString{it}

        //if(tripCursor.moveToFirst()) {
        //    tripCursor.moveToFirst()

        //}
        //test.setText(tripCursor.getColumnName(1).toString())
        //test.setText(t)
    }
}

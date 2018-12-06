package cz.martinek.stepan.kayaklog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cz.martinek.stepan.kayaklog.database.DBHelper
import org.w3c.dom.Text

class AchievementActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)

        val dbHandler = DBHelper(this, null,null,1)
        val tripCursor = dbHandler.getListCursor()


        val test = findViewById<TextView>(R.id.testAchievement)
        var col = tripCursor.columnNames

        val t = col.joinToString{it}

        if(tripCursor.moveToFirst()) {
            tripCursor.moveToFirst()
            
        }
        //test.setText(tripCursor.getColumnName(1).toString())
        test.setText(t)
    }
}

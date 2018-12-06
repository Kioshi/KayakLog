package cz.martinek.stepan.kayaklog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import cz.martinek.stepan.kayaklog.database.DBHelper

class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val dbHandler = DBHelper(this, null,null,1)
        val cursor = dbHandler.getListCursor()

        val listView = findViewById<ListView>(R.id.tripListView)
        val tripAdapter = tripCursorAdapter(this, cursor)
        listView.adapter = tripAdapter
    }
}

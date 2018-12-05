package cz.martinek.stepan.kayaklog.database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cz.martinek.stepan.kayaklog.model.Path
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class DBHelper (context: Context, name: String?,
                factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
/*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST)
  */

        val CREATE_TEST_TABLE = ("CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_ID_USERS + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT," + COLUMN_DURATION + " INTEGER" + ")")


        val CREATE_USERS_TABLE = ("CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_ID_USERS + " INTEGER PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT" + ")")


        val CREATE_TRIPS_TABLE = ("CREATE TABLE " +
                TABLE_TRIPS + "("
                + COLUMN_ID_TRIPS + " INTEGER PRIMARY KEY,"
                + COLUMN_DESC + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PUBLIC + " INTEGER,"
                + COLUMN_DURATION + " INTEGER,"
                + COLUMN_TIME_CREATED + " TEXT,"
                + COLUMN_PATH + " TEXT" + ")")

        //db.execSQL(CREATE_TEST_TABLE)
        db.execSQL(CREATE_TRIPS_TABLE)
       // db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        /*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST)
        onCreate(db)
*/
    }


    // Set Static variables for the class
    companion object {

        //General
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "kayakDB2.db"



        // User Table
        val TABLE_USERS = "users"
        val COLUMN_ID_USERS = "_id"
        val COLUMN_USERNAME = "username"
        //val COLUMN_TRIPS = "trips"


        // Trip Table
        val TABLE_TRIPS = "trips"
        val COLUMN_ID_TRIPS = "_id"
        val COLUMN_DESC = "desc"
        val COLUMN_NAME = "name"
        val COLUMN_PUBLIC = "public"
        val COLUMN_DURATION = "duration"
        val COLUMN_TIME_CREATED = "timeCreated"
        val COLUMN_PATH = "path"

        // TestTable
        val TABLE_TEST = "test"
        val COLUMN_ID_TEST= "id"

    }

    fun checkTable(): Int{
        var count = 0

        val db = this.writableDatabase

        val cursor = db.rawQuery(
            "select DISTINCT tbl_name from sqlite_master where tbl_name = '"
    + TABLE_TRIPS + "'", null)

        count = cursor.count
        return count
    }


    fun addTrip(trip: Trip){
        val values = ContentValues()
        //values.put(COLUMN_ID_TRIPS, trip.id)
        values.put(COLUMN_DESC, trip.desc)
        values.put(COLUMN_NAME, trip.name)
        values.put(COLUMN_PUBLIC, trip.public)
        values.put(COLUMN_DURATION, trip.duration)
        values.put(COLUMN_TIME_CREATED, trip.timeCreated.toString())

        var pathString = ""

        var tempArray = trip.path

        if (tempArray != null) {
            for (i in tempArray.indices) {

            var lat = tempArray[i].lat
            var long = tempArray[i].long


                pathString.plus("lat" + lat + "long" + long + ":")

            }
            values.put(COLUMN_PATH, pathString)
        }

        val db = this.writableDatabase

        db.insert(TABLE_TRIPS, null, values)
        db.close()
    }


    fun addUser(user: User){

        val values = ContentValues()
        values.put(COLUMN_USERNAME, user.username)

        val db = this.writableDatabase

        db.insert(TABLE_USERS, null, values)
        db.close()
    }


    fun getTrip(id: Int): String{
        var name: String = "sads"

        val query =
            "SELECT $COLUMN_NAME FROM $TABLE_TRIPS WHERE $COLUMN_ID_TRIPS = \"$id\""
        //"SELECT COUNT(*) FROM $TABLE_TRIPS"
        //"SELECT * FROM $TABLE_TRIPS"
            //"SELECT * FROM $TABLE_TRIPS WHERE $COLUMN_ID_TRIPS = \"$id\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)


        if (cursor.moveToFirst()) {
            name = cursor.getString(0)

           // name = cursor.count.toString()
        }
        //name = cursor.count.toString()

/*

        //var trip: Trip? = null

        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            val id = Integer.parseInt(cursor.getString(0))
            val desc = cursor.getString(1)
            name = cursor.getString(2)

            val public: Boolean = TRUE

            if(cursor.getInt(3) == 1){
                val public: Boolean = TRUE
            }else{
                val public: Boolean = FALSE
            }

            val duration = cursor.getInt(4)
            val timeCreated = cursor.getString(5)

            val path: List<Path>


/*
            trip = Trip(id, desc, name, public, duration, timeCreated,
                path)

*/
            cursor.close()
        }

        db.close()

        */
        return name
    }


    fun findUser(username: String): User?{
        val query =
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = \"$username\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var user: User? = null

        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            val id = Integer.parseInt(cursor.getString(0))
            val username = cursor.getString(1)
            user = User(id, username)
            cursor.close()

        }
        db.close()
        return user
    }
}
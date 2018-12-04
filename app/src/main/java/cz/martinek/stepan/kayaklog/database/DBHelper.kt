package cz.martinek.stepan.kayaklog.database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, name: String?,
                factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {


        val CREATE_USERS_TABLE = ("CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_ID_USERS + " INTEGER PRIMARY KEY," +
                COLUMN_USERNAME
                + " TEXT" + ")")

        val CREATE_TRIPS_TABLE = ("CREATE TABLE " + TABLE_TRIPS + "("
                + COLUMN_DESC + "TEXT,"
                + COLUMN_NAME + "TEXT,"
                + COLUMN_PUBLIC + "INTENGER,"
                + COLUMN_DURATION + "INTENGER,"
                + COLUMN_TIME_CREATED + "TEXT,"
                + COLUMN_PATH + "TEXT" + ")")

        db.execSQL(CREATE_TRIPS_TABLE)
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)

    }


    // Set Static variables for the class
    companion object {

        //General
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "kayakDB.db"



        // User Table
        val TABLE_USERS = "users"
        val COLUMN_ID_USERS = "_id"
        val COLUMN_USERNAME = "username"
        val COLUMN_TRIPS = "trips"


        // Trip Table

        val TABLE_TRIPS = "trips"
        val COLUMN_DESC = "desc"
        val COLUMN_NAME = "name"
        val COLUMN_PUBLIC = "public"
        val COLUMN_DURATION = "duration"
        val COLUMN_TIME_CREATED = "timeCreated"
        val COLUMN_PATH = "path"


    }
    fun addTrip(trip: Trip){

        val values = ContentValues()
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
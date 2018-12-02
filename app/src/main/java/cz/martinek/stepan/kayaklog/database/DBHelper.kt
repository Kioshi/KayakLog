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



        db.execSQL(CREATE_USERS_TABLE)
        //db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)

    }


    // Set Static variables for the class
    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "kayakDB.db"



        // User Table
        val TABLE_USERS = "users"
        val COLUMN_ID_USERS = "_id"
        val COLUMN_USERNAME = "username"

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
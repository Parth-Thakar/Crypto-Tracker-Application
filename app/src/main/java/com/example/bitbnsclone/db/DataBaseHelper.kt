package com.example.bitbnsclone.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context) :  SQLiteOpenHelper(context, databaseName,factory, version) {

    // DataBase helper class for SQLLite database
    companion object{
        internal val databaseName = "userDB"
        internal val factory = null
        internal val version = 1


    }
    // Creating the SQLLite database table
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table user(id integer primary key autoincrement,email varchar(100),password varchar(20))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    // inserting the users data credentials
    fun insertuserData(email : String, password : String)
    {
        val db : SQLiteDatabase = writableDatabase
        val values : ContentValues = ContentValues()
        values.put("email",email)
        values.put("password",password)

        db.insert("user",null,values)
        Log.e("Inserted this ","$email $password")
        db.close()
    }
    // VALIDATING THE USERD CREDENTIOALS FROM THE TABLE
    fun userPresent(email : String, password: String) :  Boolean
    {
        val db = writableDatabase
        val query = "select * from user where email = '$email' and password = '$password'"
        val cursor = db.rawQuery(query,null)
        if(cursor.count <= 0)
        {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

}
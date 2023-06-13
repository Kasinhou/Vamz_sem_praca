package com.example.geoguess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.geoguess.data.Score

const val DB_NAME = "DatabaseRanking"
const val TABLE_NAME = "Table_info"
const val COLUMN_USER = "user"
const val COLUMN_MIN = "min"
const val COLUMN_SEC = "sec"
const val COLUMN_COUNT = "count"


class Database(var context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_USER VARCHAR(100) PRIMARY KEY, $COLUMN_MIN INTEGER, $COLUMN_SEC INTEGER, $COLUMN_COUNT INTEGER);")
    }

    override fun onUpgrade(database: SQLiteDatabase?, old: Int, new: Int) {

    }

    fun insertN(user: String, min: Int, sec: Int, count: Int) {
        val database = writableDatabase
        var attributes = ContentValues()
        attributes.put(COLUMN_USER, user)
        attributes.put(COLUMN_MIN, min)
        attributes.put(COLUMN_SEC, sec)
        attributes.put(COLUMN_COUNT, count)

        var test = database.insert(TABLE_NAME, null, attributes)
        if (test == (-1).toLong())
            Toast.makeText(context, "Insert was unsuccessful.", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Check ranking for more info.", Toast.LENGTH_SHORT).show()

        database.close()
    }

    fun load() : MutableList<Score> {
        var listOfScores : MutableList<Score> = mutableListOf()
        val database = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val row = database.rawQuery(query, null)
        if (row.moveToFirst()) {
            do {
                var score = Score(
                    row.getString(0),
                    row.getString(1).toInt(),
                    row.getString(2).toInt(),
                    row.getString(3).toInt()
                )
                listOfScores.add(score)

            } while (row.moveToNext())
        }


        database.close()
        return listOfScores
    }
}

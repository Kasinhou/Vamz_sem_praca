package com.example.geoguess.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * Databaza na uchovavanie celkoveho poradia, vysledkov jednotlivych hier
 */
class Database(var context: Context): SQLiteOpenHelper(context, Constants.DB_NAME, null, 1) {
    /**
     * Prave jedenkrat sa vytvori nova databaza, do ktorej sa nasledne budu ukladat vysledky
     */
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("CREATE TABLE ${Constants.TABLE_NAME} (${Constants.COLUMN_USER} VARCHAR(100) PRIMARY KEY, " +
                "${Constants.COLUMN_MIN} INTEGER, ${Constants.COLUMN_SEC} INTEGER, ${Constants.COLUMN_COUNT} INTEGER);")
    }

    override fun onUpgrade(database: SQLiteDatabase?, old: Int, new: Int) {}

    /**
     * Vlozenie jednej n-tice (riadku) do databazy, ak sa porusi nejaka integrita, pouzivatel bude upozorneny a metoda vrati hodnotu -1
     */
    fun insertN(user: String, min: Int, sec: Int, count: Int): Long {
        val database = writableDatabase
        var attributes = ContentValues()
        attributes.put(Constants.COLUMN_USER, user)
        attributes.put(Constants.COLUMN_MIN, min)
        attributes.put(Constants.COLUMN_SEC, sec)
        attributes.put(Constants.COLUMN_COUNT, count)

        var test = database.insert(Constants.TABLE_NAME, null, attributes)
        if (test == (-1).toLong())
            Toast.makeText(context, "Insert was unsuccessful, name is already used.", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Check ranking for more info.", Toast.LENGTH_SHORT).show()

        database.close()
        return test
    }

    /**
     * Nacitanie vsetkych dat z databazy, ulozenie do zoznamu datovych tried Score
     */
    fun load() : MutableList<Score> {
        var listOfScores : MutableList<Score> = mutableListOf()
        val database = readableDatabase
        val query = "SELECT * FROM ${Constants.TABLE_NAME}"
        val row = database.rawQuery(query, null)
        if (row.moveToFirst()) {
            do {
                val score = Score(
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

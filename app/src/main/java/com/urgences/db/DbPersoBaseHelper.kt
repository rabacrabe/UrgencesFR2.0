package com.urgences.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by gtheurillat on 17/07/2018.
 */

class DbPersoBaseHelper

(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.e("DB", "CREATE TABLE $TABLE_NAME")

        val METIER_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT);"


        db.execSQL(METIER_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("DB", "SAVE TABLE $TABLE_NAME")
        val METIER_TABLE_SELECT_ALL = "SELECT * FROM $TABLE_NAME;"
        val cursor = db.rawQuery(METIER_TABLE_SELECT_ALL, null)

        Log.e("DB", "DROP TABLE $TABLE_NAME")
        val METIER_TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
        db.execSQL(METIER_TABLE_DROP)

        onCreate(db)

        Log.e("DB", "INSERT OLD RECORDS TABLE IN $TABLE_NAME")
        if (cursor.moveToFirst()) {
            do {
                val value = ContentValues()

                value.put(COL_2, cursor.getColumnIndex(COL_2))
                value.put(COL_3, cursor.getColumnIndex(COL_3))


                db.insert(TABLE_NAME, null, value)

            } while (cursor.moveToNext())
        }
    }

    companion object {

        val TABLE_NAME = "bookmark"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "NUMBER"

    }

}

package urgencesfr.gtheurillat.urgencesfr.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import urgencesfr.gtheurillat.urgencesfr.db.dao.PersoDAO

/**
 * Created by gtheurillat on 17/07/2018.
 */

class DbBaseHelper

(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    private val mHandler: DbBaseHelper? = null

    override fun onCreate(db: SQLiteDatabase) {

        this.createTableBookmark(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("DB", "UPGRADE DB ON VERSION " + oldVersion.toString() + " -> " + newVersion.toString())

        this.onUpgradeBookmark(db, oldVersion, newVersion)
    }



    private fun createTableBookmark(db: SQLiteDatabase) {

        Log.e("DB", "CREATE TABLE " + PersoDAO.TABLE_NAME)
        /*
        String METIER_TABLE_CREATE = "CREATE TABLE " + BookmarkDAO.TABLE_NAME + " (" +
                BookmarkDAO.KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookmarkDAO.NAME_SERIE + " TEXT, " +
                BookmarkDAO.NAME_CHAPTER + " TEXT, " +
                BookmarkDAO.NAME_PAGE + " TEXT, " +
                BookmarkDAO.URL + " TEXT);";
*/
        Log.e("DB", "-> " + PersoDAO.METIER_TABLE_CREATE)
        db.execSQL(PersoDAO.METIER_TABLE_CREATE)
    }



    private fun onUpgradeBookmark(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("DB", "SAVE TABLE " + PersoDAO.TABLE_NAME)
        val METIER_TABLE_SELECT_ALL = "SELECT * FROM " + PersoDAO.TABLE_NAME + ";"

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(METIER_TABLE_SELECT_ALL, null)
        } catch (e: Exception) {
            cursor = null
        }

        Log.e("DB", "DROP TABLE " + PersoDAO.TABLE_NAME)
        Log.e("DB", "-> " + PersoDAO.METIER_TABLE_DROP)
        db.execSQL(PersoDAO.METIER_TABLE_DROP)


        createTableBookmark(db)

        if (cursor != null) {
            Log.e("DB", "INSERT OLD RECORDS TABLE IN " + PersoDAO.TABLE_NAME)
            if (cursor.moveToFirst()) {
                do {
                    val value = ContentValues()

                    value.put(PersoDAO.NAME, cursor.getColumnIndex(PersoDAO.NAME))
                    value.put(PersoDAO.NUMBER, cursor.getColumnIndex(PersoDAO.NUMBER))


                    db.insert(PersoDAO.TABLE_NAME, null, value)

                } while (cursor.moveToNext())
            }
        }
    }

}

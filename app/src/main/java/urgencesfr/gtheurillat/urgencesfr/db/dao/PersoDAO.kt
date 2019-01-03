package urgencesfr.gtheurillat.urgencesfr.db.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

import java.util.ArrayList

import urgencesfr.gtheurillat.urgencesfr.db.DAOBase
import urgencesfr.gtheurillat.urgencesfr.db.model.Perso

/**
 * Created by gtheurillat on 17/07/2018.
 */

class PersoDAO(pContext: Context) : DAOBase(pContext) {

    /**
     * @param pso le métier à ajouter à la base
     */
    fun ajouter(pso: Perso) {
        open()
        Log.e("DB", "ADD ENTRY " + TABLE_NAME + " " + pso.name + " IN TABLE " + TABLE_NAME)

        val value = ContentValues()

        value.put(PersoDAO.NAME, pso.name)
        value.put(PersoDAO.NUMBER, pso.number)



        mDb.insert(PersoDAO.TABLE_NAME, null, value)
        close()
    }

    /**
     * @param pso le métier à ajouter à la base
     */
    fun supprimer(pso: Perso) {
        open()
        Log.e("DB", "DELETE ENTRY " + TABLE_NAME + " " + pso.id + " IN TABLE " + TABLE_NAME)
        mDb.delete(TABLE_NAME, "$KEY = ?", arrayOf(pso.id.toString()))
        close()
    }

    /**
     * @param id l'id métier à ajouter à la base
     */
    fun supprimer(id: Long) {
        open()
        Log.e("DB", "DELETE ENTRY $TABLE_NAME $id IN TABLE $TABLE_NAME")
        mDb.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
        close()
    }

    /**
     * @param pso le métier modifié
     */
    fun modifier(pso: Perso) {
        open()
        Log.e("DB", "UPDATE ENTRY " + pso.id + " IN TABLE " + TABLE_NAME)
        val value = ContentValues()
        value.put(NAME, pso.name)
        value.put(NUMBER, pso.number)


        mDb.update(TABLE_NAME, value, "$KEY = ?", arrayOf(pso.id.toString()))
        close()
    }

    /**
     * @param url l'identifiant du métier à récupérer
     */
    fun selectionnerFromUrl(url: String): Perso? {

        open()

        Log.e("DB", "GET ENTRY WITH URL $url IN TABLE $TABLE_NAME")
        val cursor = mDb.rawQuery("select * from $TABLE_NAME where url='$url';", null)
        var perso: Perso? = null

        if (cursor.moveToFirst()) {
            perso = Perso()
            perso.id = (cursor.getInt(cursor.getColumnIndex(KEY)).toLong())
            perso.name = (cursor.getString(cursor.getColumnIndex(NAME)))
            perso.number = (cursor.getString(cursor.getColumnIndex(NUMBER)))

        }

        close()
        return perso
    }

    /**
     * @param name l'identifiant du métier à récupérer
     */
    fun selectionnerFromPerso(name: String): Perso? {

        open()

        Log.e("DB", "GET ENTRY WITH $NAME=$name IN TABLE $TABLE_NAME")
        val cursor = mDb.rawQuery("select * from $TABLE_NAME where $NAME='$name';", null)
        var perso: Perso? = null

        if (cursor.moveToFirst()) {
            perso = Perso()
            perso.id = (cursor.getInt(cursor.getColumnIndex(KEY)).toLong())
            perso.name = (cursor.getString(cursor.getColumnIndex(NAME)))
            perso.number = (cursor.getString(cursor.getColumnIndex(NUMBER)))

        }

        close()
        return perso
    }


    fun selectionnerAll(): ArrayList<Perso> {
        Log.e("DB", "GET ALL ENTRIES IN TABLE $TABLE_NAME")
        open()
        val cursor = mDb.rawQuery("select * from $TABLE_NAME;", null)
        val lstPersos = ArrayList<Perso>()

        if (cursor.moveToFirst()) {
            do {
                val perso = Perso()
                perso.id = (cursor.getInt(cursor.getColumnIndex(KEY)).toLong())
                perso.name = (cursor.getString(cursor.getColumnIndex(NAME)))
                perso.number = (cursor.getString(cursor.getColumnIndex(NUMBER)))


                lstPersos.add(perso)

            } while (cursor.moveToNext())
        }

        close()

        Log.e("DB", "NB ENTRIES = " + lstPersos.size.toString())

        return lstPersos
    }

    companion object {
        val TABLE_NAME = "perso"
        val KEY = "ID"
        val NAME = "NAME_SERIE"
        val NUMBER = "NAME_CHAPTER"


        val METIER_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                NUMBER + " TEXT);"

        val METIER_TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }

}

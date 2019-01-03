package urgencesfr.gtheurillat.urgencesfr.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/**
 * Created by gtheurillat on 17/07/2018.
 */

abstract class DAOBase(pContext: Context) {

    var mDb: SQLiteDatabase? = null
        protected set

    protected var mHandler: DbBaseHelper? = null

    init {

        this.mHandler = DbBaseHelper(pContext, NOM, null, VERSION)
    }

    fun open(): SQLiteDatabase? {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        Log.e("DB", "GET WRITABLE DATABASE")
        mDb = mHandler!!.writableDatabase
        return mDb
    }

    fun close() {
        mDb!!.close()
    }

    companion object {
        // Nous sommes à la première version de la base
        // Si je décide de la mettre à jour, il faudra changer cet attribut
        protected val VERSION = 1
        // Le nom du fichier qui représente ma base
        protected val NOM = "urgencesfr.db"
    }
}

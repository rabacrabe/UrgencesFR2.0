package urgencesfr.gtheurillat.urgencesfr.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import urgencesfr.gtheurillat.urgencesfr.db.model.Perso
import urgencesfr.gtheurillat.urgencesfr.R
import urgencesfr.gtheurillat.urgencesfr.model.Pro
import java.util.ArrayList
import android.widget.LinearLayout






// Custom list item class for menu items
class PersosAdapter(private val context: Context, items: ArrayList<Perso>) : BaseAdapter() {

    private val items: ArrayList<Perso>

    init {
        this.items = ArrayList<Perso>()
        for (item in items) {
            this.items.add(item)
        }
    }

    fun addItem(perso:Perso){
        this.items.add(perso)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Perso {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var persoView = inflator.inflate(R.layout.list_perso_item, null)

        if (convertView == null) {
            // Get the current list item
            val item = items[position]
            // Get the layout for the list item

            val txtLibelle = persoView.findViewById<View>(R.id.persoName) as TextView
            txtLibelle.setText(item.name)

            val txtGenre = persoView.findViewById<View>(R.id.persoNumber) as TextView
            txtGenre.setText(item.number)

        }  else {
            persoView = convertView
        }

        return persoView

    }

    fun remove(item: Int) {
        items.removeAt(item)
    }



}
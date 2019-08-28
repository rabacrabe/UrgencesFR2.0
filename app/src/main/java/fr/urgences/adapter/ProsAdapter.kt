package fr.urgences.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import fr.urgences.model.Pro
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.tab_pros_content.view.*
import com.urgences.R
import android.widget.*
import android.widget.LinearLayout
import fr.urgences.util.launchCall
import fr.urgences.util.launchSms


class ProsAdapter : BaseAdapter {
    var prosList = ArrayList<Pro>()
    var context: Context? = null

    constructor(context: Context, prosList: ArrayList<Pro>) : super() {
        this.context = context
        this.prosList = prosList
    }

    // 2
    override fun getCount(): Int {
        return prosList.size
    }

    // 3
    override fun getItem(position: Int): Pro {
        return prosList.get(position)
    }

    override fun getItemId(position: Int): Long {
       return getItem(position).number;
        //return 0
    }

    // 5
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val pro : Pro = getItem(position)

        //var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //var proView = inflator.inflate(R.layout.tab_pros_content, null)

        val proView = LayoutInflater.from(context).inflate(R.layout.tab_pros_content, parent, false) as LinearLayout

       // if (convertView == null) {
            proView.imgPro.setImageResource(pro.img!!)
            proView.namePro.text = pro.name!!

            proView.imgPro.setOnClickListener {
                if (pro.number == 114.toLong()) {
                    //sourds et malentendants
                    launchSms(context!!, pro)
                }
                else {
                    if (pro.number == 999.toLong()) {
                        //centre anti poison
                        displayCentresPoison(context!!)
                    }
                    else {
                        launchCall(context!!, pro.name.toString(), pro.number.toString())
                    }
                }

            }
       // }
       // else {
       //     proView = convertView
       // }

        return proView
    }

    fun alert_error(context: Context, message:String) {
        val builder_error = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder_error.setTitle("ERREUR")
        builder_error.setMessage(message)
        val dialog_error: AlertDialog = builder_error.create()

        // Display the alert dialog on app interface
        dialog_error.show()
    }



    fun displayCentresPoison(context:Context){
        class CentreAntiPoison {
            public  var libelle: String
            public  var number: String

            constructor(libelle: String, number: String) {
                this.libelle = libelle
                this.number = number
            }
        }

        var listCentres : ArrayList<CentreAntiPoison> = ArrayList()
        listCentres.add(CentreAntiPoison("ANGERS", "02 41 48 21 21"))
        listCentres.add(CentreAntiPoison("BORDEAUX", "0556964080"))
        listCentres.add(CentreAntiPoison("LILLE", "0800595959"))
        listCentres.add(CentreAntiPoison("LYON", "0472116911"))
        listCentres.add(CentreAntiPoison("MARSEILLE", "0491752525"))
        listCentres.add(CentreAntiPoison("NANCY", "0383225050"))
        listCentres.add(CentreAntiPoison("PARIS", "0140054848"))
        listCentres.add(CentreAntiPoison("STRASBOURG", "0388373737"))
        listCentres.add(CentreAntiPoison("TOULOUSE", "0561777447"))


        // setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Veuillez sélèctionnner un centre anti-poison")

// add a list
        val centres = arrayOf("ANGERS", "BORDEAUX", "LILLE", "LYON", "MARSEILLE", "NANCY", "PARIS", "STRASBOURG", "TOULOUSE")
        builder.setItems(centres) { _, which ->
            when (which) {
                0 -> launchCall(context, "Centre anti-poison " + listCentres[0].libelle, listCentres[0].number)
                1 -> launchCall(context, "Centre anti-poison " + listCentres[1].libelle, listCentres[1].number)
                2 -> launchCall(context, "Centre anti-poison " + listCentres[2].libelle, listCentres[2].number)
                3 -> launchCall(context, "Centre anti-poison " + listCentres[3].libelle, listCentres[3].number)
                4 -> launchCall(context, "Centre anti-poison " + listCentres[4].libelle, listCentres[4].number)
                5 -> launchCall(context, "Centre anti-poison " + listCentres[5].libelle, listCentres[5].number)
                6 -> launchCall(context, "Centre anti-poison " + listCentres[6].libelle, listCentres[6].number)
                7 -> launchCall(context, "Centre anti-poison " + listCentres[7].libelle, listCentres[7].number)
                8 -> launchCall(context, "Centre anti-poison " + listCentres[8].libelle, listCentres[8].number)

            }
        }

        builder.setNegativeButton("Annuler", null);

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }


}


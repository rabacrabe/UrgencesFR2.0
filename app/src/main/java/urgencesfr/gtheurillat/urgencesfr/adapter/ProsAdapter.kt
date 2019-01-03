package urgencesfr.gtheurillat.urgencesfr.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import urgencesfr.gtheurillat.urgencesfr.model.Pro
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.tab_pros_content.view.*
import urgencesfr.gtheurillat.urgencesfr.R
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.text.InputType
import android.widget.*
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.Toast
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.content.DialogInterface








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
        return prosList[position]
    }

    override fun getItemId(position: Int): Long {
       // return getItem(position).number;
        return 0
    }

    // 5
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val pro = this.prosList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var proView = inflator.inflate(R.layout.tab_pros_content, null)

        if (convertView == null) {
            proView.imgPro.setImageResource(pro.img!!)
            proView.namePro.text = pro.name!!

            proView.imgPro.setOnClickListener {
                Toast.makeText(context, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()// Initialize a new instance of
                val builder = AlertDialog.Builder(context!!)

                // Set the alert dialog title
                builder.setTitle(pro.name + "("+pro.number+")")

                // Display a message on alert dialog
                builder.setMessage("Voulez vous vraiment contacter " + pro.name)

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("Oui, contacter"){dialog, which ->
                    Toast.makeText(context, "Appel " + pro.number, Toast.LENGTH_SHORT).show()// Initialize a new instance of

                    if (pro.number == 114.toLong()) {
                        //sourds et malentendants
                        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            displayAlertSourds(context!!, pro)
                        } else {
                            alert_error(context!!, "\"Vous n'avez pas autorisé cette application à envoyer des sms\"")
                        }
                    }

                    else {
                        if (pro.number == 999.toLong()) {
                            //centre anti poison
                            displayCentresPoison(context!!, pro)
                        }
                        else {
                           launchCall(pro.name.toString(), pro.number.toString())
                        }
                    }
                }


                // Display a negative button on alert dialog
                //builder.setNegativeButton("No"){dialog,which ->

                //}


                // Display a neutral button on alert dialog
                builder.setNeutralButton("Annuler"){_,_ ->

                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()
            }
        }
        else {
            proView = convertView
        }

        return proView
    }

    fun alert_error(context: Context, message:String) {
        val builder_error = AlertDialog.Builder(context!!)

        // Set the alert dialog title
        builder_error.setTitle("ERREUR")
        builder_error.setMessage(message)
        val dialog_error: AlertDialog = builder_error.create()

        // Display the alert dialog on app interface
        dialog_error.show()
    }

    fun displayAlertSourds(context:Context, pro: Pro){
        val alert = AlertDialog.Builder(context)
        var message:EditText?=null

        // Builder
        with (alert) {
            setTitle("Veuillez écrire ci-dessous le message à envoyé aux urgences")

            val input = EditText(context)
            input.setLines(8)
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)

            input.layoutParams = lp
            alert.setView(input) // uncomment this line


            alert.setPositiveButton("Envoyer") {alert, which ->
                Toast.makeText(context, "Sms envoyé!", Toast.LENGTH_SHORT).show()// Initialize a new instance of
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(pro.number.toString(), null, input.text.toString(), null, null)
            }

            alert.setNegativeButton("Annuler") {
                dialog, whichButton ->
                //showMessage("Close the game or anything!")
                dialog.dismiss()
            }
        }

        // Dialog
        val dialog = alert.create()
        dialog.show()
    }

    fun displayCentresPoison(context:Context, pro: Pro){
        class CentreAntiPoison {
            public lateinit var libelle: String
            public lateinit var number: String

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
        builder.setItems(centres) { dialog, which ->
            when (which) {
                0 -> launchCall("Centre anti-poison "+listCentres[0].libelle, listCentres[0].number)
                1 -> launchCall("Centre anti-poison "+listCentres[1].libelle, listCentres[1].number)
                2 -> launchCall("Centre anti-poison "+listCentres[2].libelle, listCentres[2].number)
                3 -> launchCall("Centre anti-poison "+listCentres[3].libelle, listCentres[3].number)
                4 -> launchCall("Centre anti-poison "+listCentres[4].libelle, listCentres[4].number)
                5 -> launchCall("Centre anti-poison "+listCentres[5].libelle, listCentres[5].number)
                6 -> launchCall("Centre anti-poison "+listCentres[6].libelle, listCentres[6].number)
                7 -> launchCall("Centre anti-poison "+listCentres[7].libelle, listCentres[7].number)
                8 -> launchCall("Centre anti-poison "+listCentres[8].libelle, listCentres[8].number)

            }
        }

        builder.setNegativeButton("Annuler", null);

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    fun launchCall(name:String, number:String) {
        Toast.makeText(context, name +"("+number+") " +"Appel en cours ...", Toast.LENGTH_SHORT).show()// Initialize a new instance of
        val intent = Intent(Intent.ACTION_CALL)
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            intent.data = Uri.parse("tel:" + number)
            context!!.startActivity(intent)
        } else {
            alert_error(context!!, "\"Vous n'avez pas autorisé cette application à passer des appels\"")
        }

    }

}


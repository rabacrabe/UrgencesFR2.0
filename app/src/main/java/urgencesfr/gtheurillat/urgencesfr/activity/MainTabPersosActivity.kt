package urgencesfr.gtheurillat.urgencesfr.activity

import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import urgencesfr.gtheurillat.urgencesfr.R
import android.text.method.ScrollingMovementMethod
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main_tab_persos.*
import urgencesfr.gtheurillat.urgencesfr.db.dao.PersoDAO
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.*
import urgencesfr.gtheurillat.urgencesfr.db.model.Perso
import urgencesfr.gtheurillat.urgencesfr.adapter.PersosAdapter;
import urgencesfr.gtheurillat.urgencesfr.model.Pro
import android.widget.AdapterView



class MainTabPersosActivity : Fragment() {

    var persoDAO: PersoDAO? = null
    var listView: ListView? = null
    var listDetail:ArrayList<Perso>? = null
    var listAdapter:PersosAdapter? = null

    companion object {

        fun newInstance(): MainTabPersosActivity {
            val args = Bundle()

            val fragment = MainTabPersosActivity()
            fragment.arguments = args



            return fragment
        }

        /*
        public override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // favDAO = FavorisDAO(getActivity())

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.activity_main_tab_persos, container, false)

        val titreTextView = view.findViewById(R.id.textView) as TextView
        titreTextView.setText("CUSTOMS PERSOS")

        return view
    }
*/
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_main_tab_persos, container, false)

        val titreTextView = view.findViewById(R.id.textView) as TextView

        persoDAO = PersoDAO(view.context);
        listDetail = persoDAO!!.selectionnerAll();

        listAdapter = PersosAdapter(view.context, listDetail!!)


        persoDAO = PersoDAO(view.context)

        listView = view.findViewById(R.id.lst_perso) as ListView

        //titreTextView.setText("CUSTOMS PERSOS")


        listView!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val current_perso = listDetail!!.get(position)

            val builder = AlertDialog.Builder(context!!)
            // Display a message on alert dialog
            builder.setMessage("Voulez vous vraiment contacter " + current_perso.name + "(" + current_perso.number + ")")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Oui, contacter"){dialog, which ->
                Toast.makeText(context, "Appel " + current_perso.number, Toast.LENGTH_SHORT).show()// Initialize a new instance of

                launchCall(current_perso.name, current_perso.number)

            }

            builder.setNeutralButton("Annuler"){_,_ ->

            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
            //return true;
        })







        listView!!.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
            internal var pos: Int = 0

            internal var dialogClickListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {

                        val current_perso : Perso = listDetail!!.get(pos)
                        //val pers = persoDAO!!.selectionnerFromName(current_perso.name)
                        persoDAO!!.supprimer(current_perso)
                        Toast.makeText(
                                view.context, "Numéro personnel supprimé!", Toast.LENGTH_SHORT
                        ).show()

                        //MAJ listview
                        listAdapter!!.remove(pos)
                        listAdapter!!.notifyDataSetChanged()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }//No button clicked
            }

            override fun onItemLongClick(arg0: AdapterView<*>, arg1: View, position: Int, id: Long): Boolean {
                pos = position

                val builder = AlertDialog.Builder(view.context)
                builder.setMessage("Supprimer le numero personnel " + listDetail!!.get(position).name).setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show()
                return true
            }
        })


        listView!!.setAdapter(listAdapter)

        val add_persos = view.findViewById<View>(R.id.add_persos) as FloatingActionButton
        add_persos.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           //         .setAction("Action", null).show()

            displayAlertAddPerso(view.context)
        }

        return view
    }

    fun displayAlertAddPerso(context: Context){
        val alert = AlertDialog.Builder(context)
        var message: EditText?=null

        // Builder
        with (alert) {
            setTitle("Ajouter un nouveau numéro personnel")

            val layout = LinearLayout(context)
            val parms = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = parms


            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)

            val inputname = EditText(context)
            inputname.setHint("Libelle")
            inputname.layoutParams = lp
            //alert.setView(inputname) // uncomment this line

            val inputnumber = EditText(context)
            inputnumber.setHint("Numéro")
            inputnumber.setInputType(InputType.TYPE_CLASS_NUMBER)
            inputnumber.layoutParams = lp
            //alert.setView(inputnumber) // uncomment this line

            layout.addView(inputname)
            layout.addView(inputnumber)

            alert.setView(layout) // uncomment this line

            alert.setPositiveButton("Ajouter") {alert, which ->
                Toast.makeText(context, "Nouveau muméro ajouté!", Toast.LENGTH_SHORT).show()// Initialize a new instance of

                //ajout dans la bdd

                val newpers : Perso = Perso(inputname.text.toString(), inputnumber.text.toString())
                persoDAO!!.ajouter(newpers)
                //MAJ listview
                listAdapter!!.addItem(newpers)
                listAdapter!!.notifyDataSetChanged()
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

    fun alert_error(context: Context, message:String) {
        val builder_error = AlertDialog.Builder(context!!)

        // Set the alert dialog title
        builder_error.setTitle("ERREUR")
        builder_error.setMessage(message)
        val dialog_error: AlertDialog = builder_error.create()

        // Display the alert dialog on app interface
        dialog_error.show()
    }

    /*
    fun launchCall(name:String?, number:String?) {
        Toast.makeText(context, name +"("+number+") " +"Appel en cours ...", Toast.LENGTH_SHORT).show()// Initialize a new instance of
        val intent = Intent(Intent.ACTION_CALL)
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            intent.data = Uri.parse("tel:" + number)
            context!!.startActivity(intent)
        } else {
            alert_error(context!!, "Vous n'avez pas autorisé cette application à passer des appels")
        }

    }
*/

    fun launchCall(name:String?, number:String?) {
        Toast.makeText(context, name +"("+number+") " +"Appel en cours ...", Toast.LENGTH_SHORT).show()// Initialize a new instance of

        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + number)
            context!!.startActivity(intent)
        } else {
            val listPermissions = listOf<String>(
                    Manifest.permission.CALL_PHONE
            )
            ActivityCompat.requestPermissions(context as Activity, listPermissions.toTypedArray(), 123)
        }
    }

}
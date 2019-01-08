package com.urgences.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import com.urgences.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.urgences.db.dao.PersoDAO
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.*
import com.urgences.db.model.Perso
import com.urgences.adapter.PersosAdapter;
import android.widget.AdapterView
import com.urgences.util.launchCall


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


        listView!!.setOnItemClickListener { parent, view, position, id ->
            val current_perso = listDetail!!.get(position)
            launchCall(context!!, current_perso.name!!, current_perso.number!!)

        }


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
                listDetail!!.add(newpers)
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

}
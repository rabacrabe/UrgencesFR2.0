package urgencesfr.gtheurillat.urgencesfr.activity

import android.content.Context
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import urgencesfr.gtheurillat.urgencesfr.R
import android.widget.GridView
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main_tab_pros.*
import urgencesfr.gtheurillat.urgencesfr.adapter.ProsAdapter
import urgencesfr.gtheurillat.urgencesfr.model.Pro


class MainTabProsActivity : Fragment() {

    var adapter: ProsAdapter? = null
    var prosList = ArrayList<Pro>()

    companion object {

        fun newInstance(): MainTabProsActivity {
            val args = Bundle()

            val fragment = MainTabProsActivity()
            fragment.arguments = args

            return fragment
        }

    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prosList.add(Pro("Police", 17, R.drawable.police))
        prosList.add(Pro("Pompier", 18, R.drawable.pompier))
        prosList.add(Pro("Samu", 15, R.drawable.samu))
        prosList.add(Pro("Samu Sociale", 115, R.drawable.samu_sociale))
        prosList.add(Pro("Sos Medecin", 3624, R.drawable.sos_medecin))
        prosList.add(Pro("Sourds et malentendants", 114, R.drawable.sourds))
        prosList.add(Pro("Sauvetage Aeronautique", 191, R.drawable.aeronautique))
        prosList.add(Pro("Alerte enlevement", 116000, R.drawable.alerte_enlevement))
        //prosList.add(Pro("Attentat", 197, R.drawable.attentat))
        prosList.add(Pro("Centre anti poison", 999, R.drawable.poisons))
        prosList.add(Pro("Urgences Europeenne", 112, R.drawable.europe))
        prosList.add(Pro("Enfant malraité", 119, R.drawable.enfant_maltraite))
        prosList.add(Pro("Sauvetage Maritime", 196, R.drawable.maritime))
        prosList.add(Pro("Violences conjugales", 3919, R.drawable.violences_conjugales))


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_main_tab_pros, container, false)

        val titreTextView = view.findViewById(R.id.textView) as TextView
        //titreTextView.setText("CUSTOMS PERSOS")

        val gridPros: GridView = view.findViewById(R.id.gridPros) as GridView

        adapter = ProsAdapter(view.context, prosList)

        gridPros.adapter = adapter

        return view
    }

}
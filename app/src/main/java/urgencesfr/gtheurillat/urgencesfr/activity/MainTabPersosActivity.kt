package urgencesfr.gtheurillat.urgencesfr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import urgencesfr.gtheurillat.urgencesfr.R
import android.widget.Toast
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main_tab_persos.*


class MainTabPersosActivity : Fragment() {
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

        // favDAO = FavorisDAO(getActivity())


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_main_tab_persos, container, false)

        val titreTextView = view.findViewById(R.id.textView) as TextView

        titreTextView.setText("CUSTOMS PERSOS")


        val add_persos = view.findViewById<View>(R.id.add_persos) as FloatingActionButton
        add_persos.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        return view
    }

}
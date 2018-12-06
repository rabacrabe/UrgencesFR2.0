package urgencesfr.gtheurillat.urgencesfr.activity

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import urgencesfr.gtheurillat.urgencesfr.R

class MainTabProsActivity : Fragment() {

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

        // favDAO = FavorisDAO(getActivity())

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_main_tab_pros, container, false)

        var titreTextView = view.findViewById(R.id.textView) as TextView

        titreTextView.setText("CUSTOMS PROS")

        return view
    }

}
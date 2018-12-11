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

        prosList.add(Pro("Police", "17",
                R.drawable.police))
        prosList.add(Pro("Pompier", "18",
                R.drawable.pompier))
        prosList.add(Pro("French Fires", "Heat a few inches of vegetable oil to 300 degrees F in a heavy pot. In 3 or 4 batches, fry the potatoes about 4 to 5 minutes per batch, or until soft. They should not be brown at all at this point-you just want to start the cooking process. Remove each batch and drain them on new, dry paper towels.",
                R.drawable.samu))
        prosList.add(Pro("Honey", "While it is less likely that anyone would do this on their own if they are not a beekeeper, this might be useful for those who aspire to become one. Bees are really great and easy to keep, even in the urban environment! As Novella Carpenter calls them, bees are &quot;gateway animal for urban farmers&quot;. All you need is some space in the backyard/deck. The process of honey harvesting and extraction most likely happens on a separate days.",
                R.drawable.sos_medecin))
        prosList.add(Pro("Strawberry", "Preparation. Coarsely mash strawberries with sugar, lemon juice, and salt using a potato masher in a large bowl. Let stand, stirring and mashing occasionally, 10 minutes. Transfer half of strawberry mixture to a blender and purée with cream until smooth. Freeze mixture in ice cream maker.",
                R.drawable.sourds))
        prosList.add(Pro("Sugar cubes", "Sugar cubes are extremely simple to make at home - all you need is sugar and water. In addition to standard cubes, you can add color and flavor to add fun flair to a tea party or another gathering. Learn how to make sugar cubes using two different methods: using a pan in the oven or an ice cube tray you leave out overnight.",
                R.drawable.aeronautique))


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_main_tab_pros, container, false)

        val titreTextView = view.findViewById(R.id.textView) as TextView
        titreTextView.setText("CUSTOMS PERSOS")

        val gridPros: GridView = view.findViewById(R.id.gridPros)

        adapter = ProsAdapter(view.context, prosList)

        gridPros.adapter = adapter

        return view
    }

}
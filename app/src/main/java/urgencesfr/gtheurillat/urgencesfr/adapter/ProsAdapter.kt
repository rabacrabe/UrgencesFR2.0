package urgencesfr.gtheurillat.urgencesfr.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import urgencesfr.gtheurillat.urgencesfr.model.Pro
import android.view.LayoutInflater
import android.widget.ImageView
import kotlinx.android.synthetic.main.tab_pros_content.view.*
import urgencesfr.gtheurillat.urgencesfr.R


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
        return position.toLong()
    }

    // 5
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val pro = this.prosList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var proView = inflator.inflate(R.layout.tab_pros_content, null)
        proView.imgPro.setOnClickListener {
/*
            val intent = Intent(context, FoodDetails::class.java)
            intent.putExtra("name", pro.name!!)
            intent.putExtra("description", pro.description!!)
            intent.putExtra("image", pro.image!!)
            context!!.startActivity(intent)
            */
        }
        proView.imgPro.setImageResource(pro.img!!)
        proView.namePro.text = pro.name!!

        return proView
    }

}


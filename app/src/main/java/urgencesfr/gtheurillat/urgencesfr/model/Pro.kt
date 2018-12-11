package urgencesfr.gtheurillat.urgencesfr.model

import android.widget.ImageView

class Pro {

    var name: String? = null
    var number: String? = null
    var img: Int? = null

    constructor(name: String, number: String, img: Int) {
        this.name = name
        this.number = number
        this.img = img
    }
}
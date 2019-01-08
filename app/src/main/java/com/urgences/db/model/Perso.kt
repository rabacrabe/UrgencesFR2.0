package com.urgences.db.model

/**
 * Created by gtheurillat on 24/07/2018.
 */

class Perso {
    var id: Long = 0
    var name: String? = null
    var number: String? = null

    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    constructor() {

    }
}

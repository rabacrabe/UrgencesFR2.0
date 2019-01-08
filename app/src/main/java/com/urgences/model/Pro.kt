package com.urgences.model

class Pro {


    var name: String? = null
    var number: Long
    var img: Int? = null

    constructor(name: String, number: Long, img: Int) {
        this.name = name
        this.number = number
        this.img = img
    }
}
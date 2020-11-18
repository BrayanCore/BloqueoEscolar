package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

class StructOptions : Serializable {

    var optionOne: String = ""
    var optionTwo: String = ""
    var optionThree: String = ""
    var optionFour: String = ""

    constructor()

    constructor (
        optionOne: String,
        optionTwo: String,
        optionThree: String,
        optionFour: String,
    ) {
        this.optionOne = optionOne
        this.optionTwo = optionTwo
        this.optionThree = optionThree
        this.optionFour = optionFour
    }
}
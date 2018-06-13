package com.lucciola.termination

import com.lucciola.formulaparser.Expression


class Unit(arg0: String): Termination(arg0) {

    constructor(arg0: Expression): this(arg0.evaluate())

    override fun evaluate(): String {
        return this.value
    }

}

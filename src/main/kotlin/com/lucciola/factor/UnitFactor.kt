package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression

class UnitFactor: Factor {

    private lateinit var outSideUnit: Expression

    constructor(arg0: Expression,  arg1: Expression) {
        this.child = arg0
        outSideUnit = arg1
    }

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        if (this.child.evaluate() == "void^0") {
            return outSideUnit.evaluate()
        } else {
            throw RuntimeErrorException("A unit that is in brackets must be void.")
        }
    }

}

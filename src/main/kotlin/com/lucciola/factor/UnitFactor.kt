package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.termination.Unit

class UnitFactor(arg0: Unit,  arg1: Unit) : Factor(arg0) {

    private var outSideUnit: Expression = arg1

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        if (this.child.evaluate() == "void^0") {
            return outSideUnit.evaluate()
        } else {
            throw RuntimeErrorException("A unit that is in brackets must be void.")
        }
    }

}

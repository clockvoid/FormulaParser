package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.termination.Unit

class UnitFactor(arg0: Unit,  var outSideUnit: Unit) : Factor(arg0) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        if (this.child.evaluate() == "void^0") {
            return this.outSideUnit.evaluate()
        } else {
            throw RuntimeErrorException("A unit that is in brackets must be void.")
        }
    }

}

package com.lucciola.formulaparser

import com.lucciola.exception.RuntimeErrorException

class TreeRoot(arg0: Expression, arg1: Expression) : Expression {

    private val child1: Expression = arg0
    private val child2: Expression = arg1

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        var unit: String = child2.evaluate()
        unit = if (unit == "void^0") {
            ""
        } else {
            "[$unit]"
        }
        return child1.evaluate() + unit
    }

}

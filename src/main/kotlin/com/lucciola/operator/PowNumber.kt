package com.lucciola.operator

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression

class PowNumber(arg0: Expression, arg1: Expression): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: ArrayList<Expression> = getChildren()
        val child1Decimal: Double = children[0].evaluate().toDouble()
        val child2Decimal: Double = children[1].evaluate().toDouble()
        return Math.pow(child1Decimal, child2Decimal).toString()
    }

}

package com.lucciola.operator

import java.math.BigDecimal

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.termination.Number
import com.lucciola.termination.Unit

class MinusUnit(arg0: Unit, arg1: Unit): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: List<Expression>  = getChildren()
        val unit1: String = children[0].evaluate()
        val unit2: String = children[1].evaluate()
        if (unit1 == unit2) {
            return unit1
        } else {
            throw RuntimeErrorException("It does not match unit $unit1 and $unit2.")
        }
    }

}

class MinusNumber(arg0: Number, arg1: Number): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: List<Expression> = getChildren()
        val child1Decimal = BigDecimal(children[0].evaluate())
        val child2Decimal = BigDecimal(children[1].evaluate())
        return child1Decimal.add(child2Decimal.negate()).toString()
    }

}

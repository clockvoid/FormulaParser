package com.lucciola.operator

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression

class PluseUnit(arg0: Expression, arg1: Expression): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: ArrayList<Expression> = getChildren()
        val unit1: String = children[0].evaluate()
        val unit2: String = children[1].evaluate()
        if (unit1 == unit2) {
            return unit1
        } else {
            throw RuntimeException("It does not match unit $unit1 and $unit2.")
        }
    }

}

package com.lucciola.factor

import java.math.BigDecimal;

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser

class SqrtUnit: Factor() {

    fun SqrtUnit(arg0: Expression) {
        this.child = arg0
    }

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val child: Expression = this.child
        val unitMap: Map<String, String> = UnitParser.createUnitMap(child.evaluate())
        for (key: String in unitMap.keys) {
            val value: String = BigDecimal(unitMap[key]).divide(BigDecimal("2")).toString()
            return UnitParser.createUnitString(hashMapOf(key to value))
        }
        return UnitParser.createUnitString(hashMapOf())
    }

}

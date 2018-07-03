package com.lucciola.factor

import java.math.BigDecimal

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser
import com.lucciola.termination.Unit
import com.lucciola.termination.Number

class SqrtUnit(arg0: Unit) : Factor(arg0) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val child: Expression = this.child
        val unitMap: Map<String, String> = UnitParser.createUnitMap(child.evaluate())
        var changedUnitMap: Map<String, String> = hashMapOf()
        for (key: String in unitMap.keys) {
            val value: String = BigDecimal(unitMap[key]).divide(BigDecimal("2")).toString()
            changedUnitMap += hashMapOf(key to value)
            println(hashMapOf(key to value))
        }
        println(changedUnitMap)
        return UnitParser.createUnitString(changedUnitMap)
    }

}

class SqrtNumber(arg0: Number) : Factor(arg0) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val num: Double = this.child.evaluate().toDouble()
        return Math.sqrt(num).toString()
    }

}

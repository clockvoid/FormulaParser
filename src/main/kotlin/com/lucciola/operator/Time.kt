package com.lucciola.operator

import java.math.BigDecimal

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser
import com.lucciola.termination.Number
import com.lucciola.termination.Unit

class TimeUnit(arg0: Unit, arg1: Unit): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: List<Expression> = getChildren()
        var unitMap1: Map<String, String> = UnitParser.createUnitMap(children[0].evaluate())
        var unitMap2: Map<String, String> = UnitParser.createUnitMap(children[1].evaluate())
        val bondedUnit: HashMap<String, String> = HashMap()
        val keySet: Set<String> = unitMap1.keys
        for (str: String in keySet) {
            val tmp: String? = unitMap2[str]
            if (tmp != null) {
                val num: String = BigDecimal(tmp).add(BigDecimal(unitMap1[str])).toString()
                unitMap1 = unitMap1.filterKeys { s -> s != str }
                unitMap2 = unitMap2.filterKeys { s -> s != str }
                if (num != "0") bondedUnit.putAll(mapOf(str to num))
            }
        }
        bondedUnit.apply {
            putAll(unitMap1)
            putAll(unitMap2)
        }
        return UnitParser.createUnitString(bondedUnit)
    }
}

class TimeNumber(arg0: Number, arg1: Number): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: List<Expression> = getChildren()
        val child1Decimal = BigDecimal(children[0].evaluate())
        val child2Decimal = BigDecimal(children[1].evaluate())
        return child1Decimal.multiply(child2Decimal).toString()
    }

}

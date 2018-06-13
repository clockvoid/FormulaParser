package com.lucciola.operator

import java.math.BigDecimal
import java.math.RoundingMode

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser
import com.lucciola.termination.Number
import com.lucciola.termination.Unit

class DivideUnit(arg0: Unit, arg1: Unit): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: ArrayList<Expression> = getChildren()
        var unitMap1: Map<String, String> = UnitParser.createUnitMap(children[0].evaluate())
        val unitMap2: Map<String, String> = UnitParser.createUnitMap(children[1].evaluate())
        val newUnitMap2 = HashMap<String, String>()
        val bondedUnit = HashMap<String, String>()
        val keySet: Set<String>  = unitMap2.keys
        for (str: String in keySet) {
            val tmp: String? = unitMap1[str]
            if (tmp != null) {
                val num: String = BigDecimal(tmp).add(BigDecimal(unitMap2[str]).negate()).toString()
                unitMap1 = unitMap1.filterKeys { s -> s != str }
                if (num != "0")
                    bondedUnit[str] = num
            } else {
                newUnitMap2[str] = BigDecimal(unitMap2[str]).negate().toString()
            }
        }
        bondedUnit.putAll(unitMap1)
        bondedUnit.putAll(newUnitMap2)
        return UnitParser.createUnitString(bondedUnit)
    }

}

class DivideNumber(arg0: Number, arg1: Number): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: ArrayList<Expression> = getChildren()
        val child1Decimal = BigDecimal(children[0].evaluate())
        val child2Decimal = BigDecimal(children[1].evaluate())
        return child1Decimal.divide(child2Decimal, Math.max(child1Decimal.scale(), child2Decimal.scale()) + 1, RoundingMode.DOWN).toString()
    }

}


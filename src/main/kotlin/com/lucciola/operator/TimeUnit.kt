package com.lucciola.operator

import java.math.BigDecimal

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser


class TimeUnit(arg0: Expression, arg1: Expression): Operator(arg0, arg1) {

    @Throws(RuntimeErrorException::class)
    override fun evaluate(): String {
        val children: ArrayList<Expression> = getChildren()
        var unitMap1: Map<String, String> = UnitParser.createUnitMap(children[0].evaluate())
        var unitMap2: Map<String, String> = UnitParser.createUnitMap(children[1].evaluate())
        val bondedUnit: HashMap<String, String> = hashMapOf()
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
        bondedUnit.putAll(unitMap1)
        bondedUnit.putAll(unitMap2)
        return UnitParser.createUnitString(bondedUnit)
    }

}

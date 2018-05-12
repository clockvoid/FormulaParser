package com.lucciola.factor

import java.math.BigDecimal

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.formulaparser.UnitParser
import com.lucciola.operator.Operator


class PowUnit: Operator() {

	fun PowUnit(arg0: Expression, arg1: Expression) {
		setChildren(arg0, arg1)
	}

    @Throws(RuntimeErrorException::class)
	override fun evaluate(): String {
		val children: ArrayList<Expression> = getChildren()
		val unitMap: Map<String, String> = UnitParser.createUnitMap(children[0].evaluate())
		val newUnitMap: HashMap<String, String> = HashMap()
		val index: String = children[1].evaluate()
		for (key: String in unitMap.keys) {
			newUnitMap.put(key, BigDecimal(unitMap[key]).multiply(BigDecimal(index)).toString())
		}
		return UnitParser.createUnitString(newUnitMap)
	}

}
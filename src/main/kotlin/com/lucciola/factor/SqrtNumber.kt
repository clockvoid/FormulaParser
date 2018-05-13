package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException;
import com.lucciola.formulaparser.Expression;

class SqrtNumber(arg0: Expression) : Factor(arg0) {

	@Throws(RuntimeErrorException::class)
	override fun evaluate(): String {
		val num: Double = this.child.evaluate().toDouble()
		return Math.sqrt(num).toString()
	}

}
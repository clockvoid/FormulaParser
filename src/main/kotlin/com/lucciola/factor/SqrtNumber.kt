package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException;
import com.lucciola.formulaparser.Expression;


class SqrtNumber: Factor {

	constructor(arg0: Expression) {
		this.child = arg0
	}

    @Throws(RuntimeErrorException::class)
	override fun evaluate(): String {
		val num: Double = this.child.evaluate().toDouble()
		return Math.sqrt(num).toString()
	}

}
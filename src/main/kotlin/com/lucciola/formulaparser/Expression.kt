package com.lucciola.formulaparser

import com.lucciola.exception.RuntimeErrorException;

interface Expression {
	@Throws(RuntimeErrorException::class)
	fun evaluate(): String
}

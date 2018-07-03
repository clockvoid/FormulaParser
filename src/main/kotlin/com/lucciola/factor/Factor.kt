package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.termination.Termination

abstract class Factor protected constructor(val child: Termination) : Expression {

    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

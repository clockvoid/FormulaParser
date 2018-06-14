package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.termination.Termination

abstract class Factor protected constructor(arg0: Termination) : Expression {

    var child: Expression = arg0
        protected set

    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

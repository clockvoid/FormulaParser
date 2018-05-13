package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException;
import com.lucciola.formulaparser.Expression;

abstract class Factor protected constructor(arg0: Expression): Expression {

    var child: Expression = arg0
        protected set

    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

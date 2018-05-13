package com.lucciola.factor

import com.lucciola.exception.RuntimeErrorException;
import com.lucciola.formulaparser.Expression;


abstract class Factor: Expression {

    lateinit var child: Expression
        protected set

    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

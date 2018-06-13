package com.lucciola.termination

import com.lucciola.exception.SyntaxErrorException
import com.lucciola.formulaparser.Expression


class Number(arg0: String): Termination(arg0) {

    init {
        if (!arg0.matches(Regex("-?\\d+(\\.\\d+)?"))) {
            throw SyntaxErrorException("Value \"$arg0\" is not Numeric")
        }
    }

    constructor(arg0: Expression): this(arg0.evaluate())

    override fun evaluate(): String {
        return this.value
    }

}

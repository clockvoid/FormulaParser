package com.lucciola.termination

import com.lucciola.formulaparser.Expression

abstract class Termination(arg0: String): Expression {

    val value: String = arg0

    abstract override fun evaluate(): String

}

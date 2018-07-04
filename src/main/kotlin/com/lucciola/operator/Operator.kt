package com.lucciola.operator

import java.util.ArrayList

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression
import com.lucciola.termination.Termination

abstract class Operator protected constructor(arg0: Termination, arg1: Termination) : Expression {

    private lateinit var child1: Expression
    private lateinit var child2: Expression

    init {
        this.setChildren(arg0, arg1)
    }

    fun getChildren(): ArrayList<Expression> {
        return arrayListOf(this.child1, this.child2)
    }

    private fun setChildren(arg0: Termination, arg1: Termination) {
        this.child1 = arg0
        this.child2 = arg1
    }

    // in Kotlin, throws is annotation.
    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

package com.lucciola.operator

import java.util.ArrayList

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression

abstract class Operator protected constructor(arg0: Expression, arg1: Expression) : Expression {

    private lateinit var child1: Expression
    private lateinit var child2: Expression

    init {
        this.setChildren(arg0, arg1)
    }

    fun getChildren(): ArrayList<Expression> {
        val children: ArrayList<Expression> = ArrayList()
        children.add(child1)
        children.add(child2)
        return children
    }

    private fun setChildren(arg0: Expression, arg1: Expression) {
        this.child1 = arg0
        this.child2 = arg1
    }

    // in Kotlin, throws is annotation.
    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

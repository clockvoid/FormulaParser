package com.lucciola.operator

import java.util.ArrayList

import com.lucciola.exception.RuntimeErrorException
import com.lucciola.formulaparser.Expression


abstract class Operator: Expression {

    private lateinit var child1: Expression
    private lateinit var child2: Expression

    fun getChildren(): ArrayList<Expression> {
        var children: ArrayList<Expression> = ArrayList<Expression>();
        children.add(child1);
        children.add(child2);
        return children;
    }

    fun setChildren(arg0: Expression, arg1: Expression) {
        this.child1 = arg0;
        this.child2 = arg1;
    }

    @Throws(RuntimeErrorException::class)
    abstract override fun evaluate(): String

}

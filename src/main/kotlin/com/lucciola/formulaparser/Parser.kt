package com.lucciola.formulaparser

import com.lucciola.exception.SyntaxErrorException
import com.lucciola.factor.*
import com.lucciola.operator.*
import com.lucciola.termination.Number
import com.lucciola.termination.Unit

class Parser(arg0: String) {

    private var splitProgram: ArrayList<String> = ArrayList(arg0.split("\\s+".toRegex()))
    private lateinit var programCounter: ListIterator<String>

    @Throws(SyntaxErrorException::class)
    fun compile(): Expression {
        this.programCounter = this.splitProgram.listIterator()
        val array: List<Expression> = this.createExpression()
        return TreeRoot(array[0], array[1])
    }

    @Throws(SyntaxErrorException::class)
    private fun createNumber(): List<Expression> {
        val str: String = this.programCounter.next()
        val array: List<String> = str.split("\\[".toRegex())
        val parsedUnit: List<String>
        parsedUnit = if (array.size == 1) {
            UnitParser.evaluatePrefix(str, "void^0")
        } else {
            UnitParser.evaluatePrefix(array[0], array[1].replace(Regex("]"), ""))
        }
        val n: Expression = Number(parsedUnit[0])
        val u: Expression = Unit(parsedUnit[1])
        var ans: List<Expression> = ArrayList()
        ans += listOf(n)
        ans += listOf(u)
        return ans
    }

    @Throws(SyntaxErrorException::class)
    private fun createExpression(): List<Expression> {
        var x: List<Expression> = this.createTerm()
        while (this.programCounter.hasNext()) {
            val str: String = programCounter.next()
            if (str == "+") {
                val t: List<Expression> = createTerm()
                var e: List<Expression> = ArrayList()
                e += listOf(PlusNumber(Number(x[0]), Number(t[0])))
                e += listOf(PlusUnit(Unit(x[1]), Unit(t[1])))
                x = e
            } else if (str == "-") {
                val t: List<Expression> = createTerm()
                var e: List<Expression> = ArrayList()
                e += listOf(MinusNumber(Number(x[0]), Number(t[0])))
                e += listOf(MinusUnit(Unit(x[1]), Unit(t[1])))
                x = e
            } else {
                programCounter.previous()
                break
            }
        }
        return x
    }

    @Throws(SyntaxErrorException::class)
    private fun createTerm(): List<Expression> {
        var x: List<Expression> = createFactor()
        while (programCounter.hasNext()) {
            val str: String = programCounter.next()
            if (str == "*") {
                val t: List<Expression> = createFactor()
                var e: List<Expression> = ArrayList()
                e += listOf(TimeNumber(Number(x[0]), Number(t[0])))
                e += listOf(TimeUnit(Unit(x[1]), Unit(t[1])))
                x = e
            } else if (str == "/") {
                val t: List<Expression> = createFactor()
                var e: List<Expression> = ArrayList()
                e += listOf(DivideNumber(Number(x[0]), Number(t[0])))
                e += listOf(DivideUnit(Unit(x[1]), Unit(t[1])))
                x = e
            } else if (str == "^") {
                val t: List<Expression> = createFactor()
                var e: List<Expression> = ArrayList()
                e += listOf(PowNumber(Number(x[0]), Number(t[0])))
                e += listOf(PowUnit(Unit(x[1]), Number(t[0])))
                x = e
            } else {
                programCounter.previous()
                break
            }
        }
        return x
    }

    private fun createUnit(arg0: List<Expression>, arg1: String): List<Expression> {
        val unit: String = arg1.replace(Regex("(\\)\\[|])"), "")
        val parsedUnit: List<String> = UnitParser.evaluatePrefix("1", unit)
        var answer: List<Expression> = ArrayList()
        answer += listOf(TimeNumber(Number(arg0[0]), Number(parsedUnit[0])))
        answer += listOf(UnitFactor(Unit(arg0[1]), Unit(parsedUnit[1])))
        return answer
    }

    @Throws(SyntaxErrorException::class)
    private fun createFactor(): List<Expression> {
        val str: String = programCounter.next()
        when {
            str.matches(Regex(".*\\d.*")) -> {
                programCounter.previous()
                return createNumber()
            }
            programCounter.hasNext() -> {
                var e: List<Expression> = createExpression() //再帰下がる
                // 下がった再帰から出てきたところ.ここで,はじめのカッコと閉じカッコの部分を確認する.
                val next: String = programCounter.next()
                if (str == "(") {
                    if (next.matches(Regex("^\\)\\[.* $"))) {
                        e = createUnit(e, next)
                    } else if (next != ")") {
                        throw SyntaxErrorException("Missing closing parenthesis.")
                    }
                } else if (str == "sqrt(") {
                    val t: ArrayList<Expression> = ArrayList()
                    t.add(SqrtNumber(Number(e[0])))
                    t.add(SqrtUnit(Unit(e[1])))
                    e = t
                    if (next.matches(Regex("^\\)\\[.* $"))) {
                        e = createUnit(e, next)
                    } else if (next != ")") {
                        throw SyntaxErrorException("Missing closing parenthesis.")
                    }
                } else {
                    throw SyntaxErrorException("Command not found : " + str.replace(Regex("\\("), ""))
                }
                return e
            }
            else -> throw SyntaxErrorException("Missing right-hand side value.")
        }
    }

}

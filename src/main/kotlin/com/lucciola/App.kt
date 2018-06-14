package com.lucciola

import com.lucciola.formulaparser.Parser

fun main(args: Array<String>) {
    // TODO: it cannot calculate units
    val parser = Parser("3 [m] * 1 [m]")
    println(parser.compile().evaluate())
}

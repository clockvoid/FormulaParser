package com.lucciola

import com.lucciola.formulaparser.Parser

fun main(args: Array<String>) {
    val parser = Parser("3[m^2] / 1[m]")
    println(parser.compile().evaluate())
}

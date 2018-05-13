package com.lucciola.termination

class Number(arg0: String): Termination(arg0) {

    override fun evaluate(): String {
        return this.value
    }

}

package com.lucciola.termination

class Unit(arg0: String): Termination(arg0) {

    override fun evaluate(): String {
        return this.value
    }

}

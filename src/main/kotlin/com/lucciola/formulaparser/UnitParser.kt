package com.lucciola.formulaparser

import java.math.BigDecimal

abstract class UnitParser {

    // singleton methods
    companion object {

        // map that expresses about prefix number of digits.
        @SuppressWarnings("serial")
        private val prefix: HashMap<String, String> = hashMapOf(
                "T" to "12",
                "G" to "9",
                "M" to "6",
                "k" to "3",
                "h" to "2",
                "da" to "1",
                "d" to "-1",
                "c" to "-2",
                "m" to "-3",
                "u" to "-6",
                "n" to "-9",
                "p" to "-12"
        )

        fun parsePrefix(arg0: String): Array<String> {
            val unit: List<String> = arg0.split("_")
            return if (unit.size == 1) {
                arrayOf("0", arg0)
            } else {
                var prefixString: String = unit[0]
                val array: List<String> = unit[1].split("\\^")
                prefixString =  if (array.size == 1) {
                    prefix[prefixString]!!
                } else {
                    BigDecimal(prefix[prefixString]).multiply(BigDecimal(array[1])).toString()
                }
                arrayOf(prefixString, unit[1])
            }
        }

        fun evaluatePrefix(arg0: String, arg1: String): List<String> {
            val unitList: List<String> = arg1.split("'")
            var number = "0"
            var unit = ""
            for (str: String in unitList) {
                val array: Array<String> = parsePrefix(str)
                number = BigDecimal(number).add(BigDecimal(array[0])).toString()
                unit += (if (unit.isNotEmpty()) "'" else "") + array[1]
            }
            val ans: ArrayList<String> = ArrayList()
            ans.add(BigDecimal(arg0).multiply(BigDecimal(Math.pow(10.0, number.toDouble()))).toString()).toString()
            ans.add(unit)
            return ans
        }

        fun createUnitMap(arg0: String): Map<String, String>  {
            val dividedUnit: List<String> = arg0.split("'")
            val unitMap: HashMap<String, String> = HashMap()
            for (str: String in dividedUnit) {
                val array: List<String> = str.split("^")
                if (array.size == 1) {
                    unitMap[array[0]] = "1"
                } else {
                    unitMap[array[0]] = array[1]
                }
            }
            return unitMap
        }

        fun createUnitString(arg0: HashMap<String, String>): String {
            var unitString = ""
            for (set: Map.Entry<String, String> in arg0.entries) {
                unitString += set.key + (if (set.value == "1") "" else "^" + (if (set.key == "void") "0" else set.value)) + "'"
            }
            if (unitString == "")unitString = "void^0"
            return unitString.replace(Regex("'$"), "")
        }
    }

}

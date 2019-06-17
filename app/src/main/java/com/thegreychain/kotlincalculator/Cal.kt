package com.thegreychain.kotlincalculator
import java.util.Stack

object EvaluateString {
    fun evaluate(strArrr: String): Double {
        var strArr = strArrr.split("")
        val operators = Stack<String>()
        val operands = Stack<Double>()

        for (str in strArr) {
            if (str.trim { it <= ' ' } == "") {
                continue
            }

            when (str) {
                "(" -> {
                }
                ")" -> {
                    val right = operands.pop()
                    val left = operands.pop()
                    val operator = operators.pop()
                    var value = 0.0
                    when (operator) {
                        "+" -> value = left + right
                        "-" -> value = left - right
                        "*" -> value = left * right
                        "/" -> value = left / right
                        else -> {
                        }
                    }
                    operands.push(value)
                }
                "+", "-", "*", "/" -> operators.push(str)
                else -> operands.push(java.lang.Double.parseDouble(str))
            }
        }

        return operands.pop()
    }

    // Driver method to test above methods
    @JvmStatic
    fun main(args: Array<String>) {
        println(EvaluateString.evaluate("10 + 2 * 6"))
        println(EvaluateString.evaluate("100 * 2 + 12"))
        println(EvaluateString.evaluate("100 * ( 2 + 12 )"))
        println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"))
    }
}
package com.thegreychain.kotlincalculator.data

import com.thegreychain.kotlincalculator.domain.repository.IValidator

object ValidatorImpl : IValidator {
    override fun validateExpression(expression: String): Boolean {

        //check for valid starting/ending chars
        if (invalidStart(expression)) return false
        if (invalidEnd(expression)) return false

        //Check for concurrent decimals and operators like "2++2"
        if (hasConcurrentOperators(expression)) return false
        if (hasConcurrentDecimals(expression)) return false
        if (hasCloseBracketFollowingRoot(expression)) return false
        if (hasConcurrentRootOrBracket(expression)) return false


        return true
    }

    private fun invalidEnd(expression: String):Boolean {
        when {
            expression.endsWith("+") -> return true
            expression.endsWith("-") -> return true
            expression.endsWith("*") -> return true
            expression.endsWith("/") -> return true
            expression.endsWith(".") -> return true
            expression.endsWith("√") -> return true
            else -> return false
        }
    }

    private fun invalidStart(expression: String):Boolean {
        when {
            expression.startsWith("+") -> return true
            expression.startsWith("-") -> return true
            expression.startsWith("*") -> return true
            expression.startsWith("/") -> return true
            expression.startsWith(".") -> return true
            expression.startsWith(")") -> return true
            else -> return false
        }
    }

    private fun hasConcurrentDecimals(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentDecimal(expression[it], expression[it + 1])) {
                            return true
                        }
                    }
                }

        return false
    }

    private fun isConcurrentDecimal(current: Char, next: Char): Boolean {
        if (current.toString() == "." && next.toString() ==".") {
            return true
        }
        return false
    }

    private fun hasConcurrentOperators(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentOperator(expression[it], expression[it + 1])) {
                            return true
                        }
                    }
                }

        return false
    }

    private fun isConcurrentOperator(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)) {
            return true
        }
        return false
    }

    private fun isOperator(char: Char): Boolean {
        return when {
        //not sure why I had to toString() but char.equals("+") was not working as expected
            char.toString() == "+" -> true
            char.toString() == "-" -> true
            char.toString() == "*" -> true
            char.toString() == "/" -> true
            else -> false
        }
    }

    private fun isBracketAndRoot(char: Char): Boolean {
        return when {
        //not sure why I had to toString() but char.equals("+") was not working as expected
            char.toString() == "√" -> true
            char.toString() == "(" -> true
            char.toString() == ")" -> true
            else -> false
        }
    }


    private fun hasConcurrentRootOrBracket(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (expression[it]==expression[it + 1]) {
                            return true
                        }
                    }
                }

        return false
    }

    private fun hasCloseBracketFollowingRoot(expression: String):Boolean {
        when {
            expression.contains("√)") -> return true
            else -> return false
        }
    }

    private fun hasOnlyBracketAndOrRoot(expression: String):Boolean {
        when {
            expression.contains("+") -> return false
            expression.startsWith("-") -> return false
            expression.startsWith("*") -> return false
            expression.startsWith("/") -> return false
            expression.startsWith(".") -> return false
            else -> return false
        }
    }
}
package com.thegreychain.kotlinclculator
import java.util.Stack

object EvaluateString {
    fun evaluate(expression: String): Int {
        val tokens = expression.toCharArray()

        // Stack for numbers: 'values'
        val values = Stack<Int>()

        // Stack for Operators: 'ops'
        val ops = Stack<Char>()

        var i = 0
        while (i < tokens.size) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ') {
                i++
                continue
            }

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                val sbuf = StringBuffer()
                // There may be more than one digits in number
                while (i < tokens.size && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++])
                values.push(Integer.parseInt(sbuf.toString()))
            } else if (tokens[i] == '(')
                ops.push(tokens[i])
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                ops.pop()
            } else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()))

                // Push current token to 'ops'.
                ops.push(tokens[i])
            }// Current token is an operator.
            // Closing brace encountered, solve entire brace
            // Current token is an opening brace, push it to 'ops'
            i++
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()))

        // Top of 'values' contains result, return it
        return values.pop()
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')')
            return false
        return if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            false
        else
            true
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    fun applyOp(op: Char, b: Int, a: Int): Int {
        when (op) {
            '+' -> return a + b
            '-' -> return a - b
            '*' -> return a * b
            '/' -> {
                if (b == 0)
                    throw UnsupportedOperationException("Cannot divide by zero")
                return a / b
            }
        }
        return 0
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
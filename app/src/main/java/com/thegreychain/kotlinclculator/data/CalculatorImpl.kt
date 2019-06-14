package com.thegreychain.kotlinclculator.data

import android.support.annotation.VisibleForTesting
import com.thegreychain.kotlinclculator.data.datamodel.ExpressionDataModel
import com.thegreychain.kotlinclculator.data.datamodel.OperandDataModel
import com.thegreychain.kotlinclculator.data.datamodel.OperatorDataModel
import com.thegreychain.kotlinclculator.domain.repository.ICalculator
import io.reactivex.Flowable
import java.lang.IllegalArgumentException
import java.util.*


object CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<ExpressionDataModel> {

        if (expression.contains('(')){
            return evaluatepar(expression)
        }else{
            return evaluate(expression)
        }

    }

    private fun evaluate(expression: String): Flowable<ExpressionDataModel> {

        //get ops and ops
        val operatorDataModels: MutableList<OperatorDataModel> = getOperators(expression)
        val operands: MutableList<OperandDataModel> = getOperands(expression)

        while (operands.size > 1) {
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operatorDataModels[0]

            //if op is * or / (evaluateFirst), or no more operatorDataModels to follow,
            // or next op is NOT (evaluateFirst)
            if (firstOperator.evaluateFirst ||
                    operatorDataModels.elementAtOrNull(1) == null ||
                    !operatorDataModels[1].evaluateFirst) {
                val result = OperandDataModel(evaluatePair(firstOperand, secondOperand, firstOperator))
                operatorDataModels.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {

                val thirdOperand = operands[2]
                val secondOperator = operatorDataModels[1]
                val result = OperandDataModel(evaluatePair(secondOperand, thirdOperand, secondOperator))

                operatorDataModels.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }
        }

        //when calculations are finished, emit the result
        return Flowable.just(ExpressionDataModel(operands[0].value, true))
    }

    @VisibleForTesting
    internal fun getOperands(expression: String): MutableList<OperandDataModel> {
        val operands = expression.split("+", "-", "/", "*")
        val outPut: MutableList<OperandDataModel> = arrayListOf()

        //Kotlin's answer to enhanced for loop
        operands.indices.mapTo(outPut) {
            OperandDataModel(operands[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun getOperators(expression: String): MutableList<OperatorDataModel> {
        //this ugly stuff is called Regex; Regular ExpressionDataModel/
        //Basically saying split based on number or decimal numbers.
        val operators = expression.split("\\d+(?:\\.\\d+)?".toRegex())
                .filterNot { it == "" }
                .toMutableList()
        val outPut: MutableList<OperatorDataModel> = arrayListOf()

        operators.indices.mapTo(outPut) {
            OperatorDataModel(operators[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun evaluatePair(firstOperand: OperandDataModel, secondOperand: OperandDataModel, operatorDataModel: OperatorDataModel): String {
        when (operatorDataModel.operatorValue) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw  IllegalArgumentException("Illegal Operator.")
    }


    fun evaluatepar(expression: String): Flowable<ExpressionDataModel> {
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
        return Flowable.just(ExpressionDataModel(values.pop().toString(),true))
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

}
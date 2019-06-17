package com.thegreychain.kotlincalculator.data

import android.support.annotation.VisibleForTesting
import android.util.Log
import com.thegreychain.kotlincalculator.data.datamodel.ExpressionDataModel
import com.thegreychain.kotlincalculator.data.datamodel.OperandDataModel
import com.thegreychain.kotlincalculator.data.datamodel.OperatorDataModel
import com.thegreychain.kotlincalculator.domain.repository.ICalculator
import io.reactivex.Flowable
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.IllegalArgumentException
import java.util.*
import android.graphics.Color.parseColor
import android.widget.Toast
import com.thegreychain.kotlincalculator.util.error.ValidationException
import net.objecthunter.exp4j.Expression


object CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<ExpressionDataModel> {


            return evaluate(expression)


    }

    private fun evaluate(expression: String): Flowable<ExpressionDataModel> {

        //get ops and ops

        val operands: MutableList<OperandDataModel> = getOperands(expression)

        if (expression.contains('(' )|| expression.contains("√")){
            //return evaluatepar(expression)
            try {

                val expressions = ExpressionBuilder(expression.replace("√","sqrt")).build()
                val result = expressions.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble())
                    operands.add(0, OperandDataModel(result.toString()))
                else
                    operands.add(0, OperandDataModel(result.toString()))

            }catch (e:Exception){

                Log.d("Exception"," message : " + e.message )


                return Flowable.just(ExpressionDataModel(e.message.toString(), false))
            }


        }else{
            val operatorDataModels: MutableList<OperatorDataModel> = getOperators(expression)
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

    fun evaluateparr(expression: String): Flowable<ExpressionDataModel> {
        var parCount=0
        var currentOperator="+"
        var operators="+-*/"
        var operand1=0.0
        var operand2="0"

        var index=0;
        val tokens = expression.toCharArray()

        while (index < tokens.size) {
            // Current token is a whitespace, skip it
            Log.i("tokensIndex", index.toString()+" "+tokens[index])
            if (tokens[index] == ' ') {
                index++
                continue
            }
            if (tokens[index] == ')') {
                if (parCount<1) {
                    index++
                    continue
                }
            }else if (tokens[index] == '(') {
                parCount++
                index++
                continue
            }

            if(parCount>0){
                var op1=0.0
                var op2="0"
                var operator="+"
                while(parCount>0){

                    if (tokens[index] == ')') {
                        op1=calculate(op1, op2, operator)
                        operand1=calculate(operand1, op1.toString(), currentOperator)
                        parCount--
                        Log.i("INDEX__", "op2.toString() "+operand2)


                        op1=0.0
                        op2="0"
                        operator="+"
//                        index++
//                        continue
                    }else{
                        if(operators.contains(tokens[index])){
                            op1=calculate(op1, op2, operator)
                            operator=tokens[index].toString()
                            Log.i("INDEX__", op1.toString()+" "+op2+" "+operator)
                            op2="0"
                        }else{
                            op2+=tokens[index]
                            Log.i("INDEX__", "OP2 "+op2)
                        }
//                        index++
                    }
                    index++
                }
            }else{
                if(operators.contains(tokens[index])){
                    operand1= calculate(operand1, operand2, currentOperator)
                    currentOperator=tokens[index].toString()
                    operand2="0"
                }else{
                    operand2+=tokens[index]
                }
                index++
            }
        }
        operand1=calculate(operand1, operand2, currentOperator)
        return Flowable.just(ExpressionDataModel(operand1.toString(),true))
    }

    fun calculate(operand1: Double, operand2: String, operator: String): Double {
        when (operator) {
            "+" -> return operand1 + operand2.toFloat()
            "-" -> return operand1 - operand2.toFloat()
            "*" -> return operand1 * operand2.toFloat()
            "/" -> {
                if (operand2.toInt() == 0)
                    throw UnsupportedOperationException("Cannot divide by zero")
                return operand1 / operand2.toFloat()
            }
        }
        return 0.0
    }

}
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
import com.thegreychain.kotlincalculator.view.CalculatorFragment
import com.thegreychain.kotlincalculator.view.IViewContract
import net.objecthunter.exp4j.Expression


object CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<ExpressionDataModel> {


            return evaluate(expression)


    }

    private fun evaluate(expression: String): Flowable<ExpressionDataModel> {
        //if expression contains ( or √ or E, use Expression Builder, else get operands and operators

        val operands: MutableList<OperandDataModel> = getOperands(expression)

        if (expression.contains('(' )|| expression.contains("√")|| expression.contains("E")){
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


                //return Flowable.just(ExpressionDataModel(e.message.toString(), false))
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
}
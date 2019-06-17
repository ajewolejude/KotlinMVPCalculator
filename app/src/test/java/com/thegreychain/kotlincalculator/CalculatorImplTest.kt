package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.data.CalculatorImpl
import com.thegreychain.kotlincalculator.data.datamodel.ExpressionDataModel
import com.thegreychain.kotlincalculator.data.datamodel.OperandDataModel
import com.thegreychain.kotlincalculator.data.datamodel.OperatorDataModel
import io.reactivex.subscribers.TestSubscriber
import junit.framework.TestCase.assertTrue
import org.junit.Test


class CalculatorImplTest {
    private val calc = CalculatorImpl

    val SIMPLE_EXPRESSION = "2+2"
    val SIMPLE_ANSWER = "4.0"

    val ROOT_EXPRESSION = "√4"
    val ROOT_ANSWER = "2.0"

    val BRACKET_EXPRESSION = "(2+2)"
    val BRACKET_ANSWER = "4.0"

    val COMPLEX_EXPRESSION = "2+2-1*3+4"
    val COMPLEX_ANSWER = "5.0"

    val VERY_COMPLEX_EXPRESSION = "√((2+2-1*3+4)*5)"
    val VERY_COMPLEX_ANSWER = "5.0"

    val OPERANDS = listOf<OperandDataModel>(
            OperandDataModel("2"),
            OperandDataModel("2"),
            OperandDataModel("1"),
            OperandDataModel("3"),
            OperandDataModel("4")
    )
    val OPERATORS = listOf<OperatorDataModel>(
            OperatorDataModel("+"),
            OperatorDataModel("-"),
            OperatorDataModel("*"),
            OperatorDataModel("+")
    )
    val INVALID_EXPRESSION_ONE = "2+"
    val INVALID_EXPRESSION_TWO = "+2"
    val INVALID_EXPRESSION_THREE = "2+-"
    val INVALID_ANSWER = "Error: Invalid ExpressionDataModel"

    /**
     * Get operands of current expression
     */
    @Test
    fun getOperands() {
        val operands: List<OperandDataModel> = calc.getOperands(COMPLEX_EXPRESSION)

        assertTrue(operands == OPERANDS)
    }

    @Test
    fun getOperators() {
        val operatorDataModels: List<OperatorDataModel> = calc.getOperators(COMPLEX_EXPRESSION)

        assertTrue(operatorDataModels == OPERATORS)
    }

    @Test
    fun onEvaluateValidSimpleExpression() {
        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(SIMPLE_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == SIMPLE_ANSWER)
    }
    //

    @Test
    fun onEvaluateValidWithRootExpression(){

        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(ROOT_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == ROOT_ANSWER)

    }
    @Test
    fun onEvaluateValidComplexExpression() {
        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(COMPLEX_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == COMPLEX_ANSWER)
    }

    @Test
    fun onEvaluateValidWithBracketExpression(){

        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(BRACKET_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == BRACKET_ANSWER)

    }

    @Test
    fun onEvaluateValidWithVeryComplexExpression(){

        val subscriber = TestSubscriber<ExpressionDataModel>()

        calc.evaluateExpression(VERY_COMPLEX_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].value == VERY_COMPLEX_ANSWER)

    }

}





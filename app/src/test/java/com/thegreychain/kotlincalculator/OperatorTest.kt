package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.data.datamodel.OperatorDataModel
import org.junit.Test



class OperatorTest {

    val MULTIPLY = "*"
    val DIVIDE = "/"
    val ADD = "+"
    val SUBTRACT = "-"

    @Test
    fun TestEvaluateFirst() {
        val testOp = OperatorDataModel(MULTIPLY)

        //assert true
        assert(testOp.evaluateFirst)
    }

    @Test
    fun TestEvaluateLast() {
        val testOp = OperatorDataModel(ADD)

        //assert true
        assert(!testOp.evaluateFirst)
    }
}
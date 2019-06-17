package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.data.ValidatorImpl
import com.thegreychain.kotlincalculator.data.datamodel.ExpressionDataModel
import com.thegreychain.kotlincalculator.domain.domainmodel.Expression
import com.thegreychain.kotlincalculator.domain.repository.ICalculator
import com.thegreychain.kotlincalculator.domain.usecase.EvaluateExpression
import io.reactivex.Flowable
import com.thegreychain.kotlincalculator.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EvaluateExpressionModelTest {

    @Mock
    lateinit var calc: ICalculator

    @Mock
    lateinit var validator: ValidatorImpl

    lateinit var useCase: EvaluateExpression

    val EXPRESSION = "2+√4"
    val ANSWER = "4"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = EvaluateExpression(calc, validator, TestScheduler())
    }

    @Test
    fun onUseCaseExecuted() {
        val subscriber = TestSubscriber<Expression>()

        Mockito.`when`(validator.validateExpression(EXPRESSION))
                .thenReturn(
                        true
                )

        Mockito.`when`(calc.evaluateExpression(EXPRESSION))
                .thenReturn(
                        Flowable.just(
                                ExpressionDataModel(ANSWER, true)
                        )
                )

        useCase.execute(EXPRESSION).subscribeWith(subscriber)

        Mockito.verify(validator).validateExpression(EXPRESSION)
        Mockito.verify(calc).evaluateExpression(EXPRESSION)

        assertTrue(subscriber.values()[0].result == ANSWER)
        assertTrue(subscriber.values()[0].successful)
    }

}
package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.domain.domainmodel.Expression
import com.thegreychain.kotlincalculator.domain.usecase.EvaluateExpression
import com.thegreychain.kotlincalculator.presenter.CalculatorPresenter
import com.thegreychain.kotlincalculator.view.IViewContract
import com.thegreychain.kotlincalculator.viewmodel.CalculatorViewModel
import io.reactivex.Flowable
import com.thegreychain.kotlincalculator.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Test behaviour of Presenter.
 *
 * Props to Antonio Leiva for explaining how to add org.mockito.plugins.MockMaker file to
 * test/resources/mockito-extensions to enable Mocking of Kotlin final classes.
 *
 * https://antonioleiva.com/mockito-2-kotlin/
 *
 */
class CalculatorPresenterTest {

    private lateinit var scheduler: TestScheduler

    private lateinit var presenter: CalculatorPresenter

    @Mock
    private lateinit var view: IViewContract.View

    @Mock
    private lateinit var viewModel: CalculatorViewModel

    //Although I personally prefer the term "Data" instead of "Model" to refer to an Architectural
    //Layer responsible for Data Management and Manipulation, you can think of calculator as the
    //"Model"; in a more classic sense of MVP
    @Mock
    private lateinit var eval: EvaluateExpression


    val EXPRESSION = "(2+2)"
    val ANSWER = "4"

    val INVALID_EXPRESSION = "2+Q"
    val INVALID_ANSWER = "Error: Invalid ExpressionDataModel"

    val VERY_COMPLEX_EXPRESSION = "√((2+2-1*3+4)*5)"
    val VERY_COMPLEX_ANSWER = "5.0"


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        scheduler = TestScheduler()

        presenter = CalculatorPresenter(view, viewModel, scheduler, eval)
    }

    /**
     * User hits evaluate, expression is valid. Should return mathematically accurate evaluation
     * of the input as a String.
     */
    @Test
    fun onEvaluateValidSimpleExpression() {
        val result = Expression.createSuccessModel(ANSWER)

        Mockito.`when`(eval.execute(EXPRESSION))
                .thenReturn(
                        Flowable.just(
                                result
                        )
                )

        Mockito.`when`(eval.execute(EXPRESSION))
                .thenReturn(
                        Flowable.just(
                                result
                        )
                )

        //this is the "Unit" what we are testing
        presenter.onEvaluateClick(EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        Mockito.verify(eval).execute(EXPRESSION)
        Mockito.verify(viewModel).setDisplayState(ANSWER)
    }

    @Test
    fun onEvaluateValidComplexExpression() {
        val result = Expression.createSuccessModel(VERY_COMPLEX_ANSWER)

        Mockito.`when`(eval.execute(VERY_COMPLEX_EXPRESSION))
                .thenReturn(
                        Flowable.just(
                                result
                        )
                )

        Mockito.`when`(eval.execute(VERY_COMPLEX_EXPRESSION))
                .thenReturn(
                        Flowable.just(
                                result
                        )
                )

        //this is the "Unit" what we are testing
        presenter.onEvaluateClick(VERY_COMPLEX_EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        Mockito.verify(eval).execute(VERY_COMPLEX_EXPRESSION)
        Mockito.verify(viewModel).setDisplayState(VERY_COMPLEX_ANSWER)
    }


    @Test
    fun onEvaluateInvalidExpression() {
        Mockito.`when`(eval.execute(INVALID_EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.just(
                                Expression.createFailureModel(INVALID_ANSWER)
                        )
                )

        presenter.onEvaluateClick(INVALID_EXPRESSION)

        Mockito.verify(eval).execute(INVALID_EXPRESSION)
        Mockito.verify(view).showError(INVALID_ANSWER)
    }

    @Test
    fun onEvaluateFatalError() {
        Mockito.`when`(eval.execute(INVALID_EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.error(Exception(INVALID_ANSWER))
                )


        presenter.onEvaluateClick(INVALID_EXPRESSION)

        Mockito.verify(eval).execute(INVALID_EXPRESSION)
        Mockito.verify(view).restartFeature()
    }


}
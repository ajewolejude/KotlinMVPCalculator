package com.thegreychain.kotlincalculator.domain.usecase

import com.thegreychain.kotlincalculator.domain.BaseUseCase
import com.thegreychain.kotlincalculator.domain.domainmodel.Expression
import io.reactivex.Flowable
import com.thegreychain.kotlincalculator.domain.repository.ICalculator
import com.thegreychain.kotlincalculator.domain.repository.IValidator
import com.thegreychain.kotlincalculator.util.error.ValidationException
import io.reactivex.schedulers.TestScheduler
import com.thegreychain.kotlincalculator.util.scheduler.BaseSchedulerProvider


class EvaluateExpression(private val calculator: ICalculator,
                         private val validator: IValidator,
                         private val scheduler: BaseSchedulerProvider) : BaseUseCase<Expression> {

    override fun execute(expression: String): Flowable<Expression> {
        //Validator operates synchronously because having to much Rx stuff here seems to freak
        //people out. Also, it really doesn't need to be thread.
        if (validator.validateExpression(expression)) {
            return calculator.evaluateExpression(expression)
                    //Note: result is something I declare, but it's type comes from the type
                    //which we are observing, which is ExpressionDataModel (see return type of ICalculator.kt)
                    .flatMap { result ->
                        Flowable.just(
                                Expression.createSuccessModel(result.value)
                        )
                    }
                    .subscribeOn(scheduler.getComputationScheduler())
        }

        return Flowable.just(
                Expression.createFailureModel(ValidationException.message)
        )
    }
}
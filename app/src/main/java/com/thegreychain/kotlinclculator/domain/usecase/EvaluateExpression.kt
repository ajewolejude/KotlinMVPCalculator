package com.thegreychain.kotlinclculator.domain.usecase

import com.thegreychain.kotlinclculator.domain.BaseUseCase
import com.thegreychain.kotlinclculator.domain.domainmodel.Expression
import io.reactivex.Flowable
import com.thegreychain.kotlinclculator.domain.repository.ICalculator
import com.thegreychain.kotlinclculator.domain.repository.IValidator
import com.thegreychain.kotlinclculator.util.error.ValidationException
import io.reactivex.schedulers.TestScheduler
import com.thegreychain.kotlinclculator.util.scheduler.BaseSchedulerProvider


/**
 * Created by R_KAY on 12/20/2017.
 */
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
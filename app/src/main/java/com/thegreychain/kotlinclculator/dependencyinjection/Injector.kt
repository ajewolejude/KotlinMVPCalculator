package com.thegreychain.kotlinclculator.dependencyinjection

import android.support.v7.app.AppCompatActivity
import com.thegreychain.kotlinclculator.data.CalculatorImpl
import com.thegreychain.kotlinclculator.data.ValidatorImpl
import com.thegreychain.kotlinclculator.domain.usecase.EvaluateExpression
import com.thegreychain.kotlinclculator.presenter.CalculatorPresenter
import com.thegreychain.kotlinclculator.util.scheduler.SchedulerProviderImpl
import com.thegreychain.kotlinclculator.view.CalculatorFragment
import com.thegreychain.kotlinclculator.view.IViewContract
import com.thegreychain.kotlinclculator.viewmodel.CalculatorViewModel
import android.arch.lifecycle.ViewModelProviders

class Injector(private var activity: AppCompatActivity) {

    private var validator: ValidatorImpl = ValidatorImpl
    private var calculator: CalculatorImpl = CalculatorImpl
    private var schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl


    fun providePresenter(view: CalculatorFragment): IViewContract.Presenter {
        return CalculatorPresenter(
                view,
                ViewModelProviders.of(activity).get(CalculatorViewModel::class.java),
                schedulerProvider,
                EvaluateExpression(calculator, validator, schedulerProvider)
        )
    }
}
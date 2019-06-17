package com.thegreychain.kotlincalculator.dependencyinjection

import android.support.v7.app.AppCompatActivity
import com.thegreychain.kotlincalculator.data.CalculatorImpl
import com.thegreychain.kotlincalculator.data.ValidatorImpl
import com.thegreychain.kotlincalculator.domain.usecase.EvaluateExpression
import com.thegreychain.kotlincalculator.presenter.CalculatorPresenter
import com.thegreychain.kotlincalculator.util.scheduler.SchedulerProviderImpl
import com.thegreychain.kotlincalculator.view.CalculatorFragment
import com.thegreychain.kotlincalculator.view.IViewContract
import com.thegreychain.kotlincalculator.viewmodel.CalculatorViewModel
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
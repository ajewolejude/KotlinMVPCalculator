package com.thegreychain.kotlincalculator.domain.repository

import com.thegreychain.kotlincalculator.data.datamodel.ExpressionDataModel

import io.reactivex.Flowable

interface ICalculator {

    //operates asynchronously via Rxjava
    fun evaluateExpression(expression: String): Flowable<ExpressionDataModel>
}
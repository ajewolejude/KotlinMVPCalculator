package com.thegreychain.kotlinclculator.domain.repository

import com.thegreychain.kotlinclculator.data.datamodel.ExpressionDataModel

import io.reactivex.Flowable

interface ICalculator {

    //operates asynchronously via Rxjava
    fun evaluateExpression(expression: String): Flowable<ExpressionDataModel>
}
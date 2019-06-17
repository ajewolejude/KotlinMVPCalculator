package com.thegreychain.kotlincalculator.domain

import io.reactivex.Flowable

interface BaseUseCase<T> {
    fun execute(expression: String): Flowable<T>
}
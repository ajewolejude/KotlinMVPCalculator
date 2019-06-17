package com.thegreychain.kotlincalculator.util.scheduler

import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    fun getComputationScheduler(): Scheduler

    fun getUiScheduler(): Scheduler
}
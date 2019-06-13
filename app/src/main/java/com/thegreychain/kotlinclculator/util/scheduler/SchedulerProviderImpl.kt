package com.thegreychain.kotlinclculator.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

object SchedulerProviderImpl: BaseSchedulerProvider {

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    override fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * testing of RxJava impl on JVM.
 *
 */
class TestScheduler: BaseSchedulerProvider {
    override fun getComputationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getUiScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}
package com.thegreychain.kotlinclculator

import com.thegreychain.kotlinclculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Allows testing of RxJava impl on JVM (we can't use an AndroidScheduler on the JVM).
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
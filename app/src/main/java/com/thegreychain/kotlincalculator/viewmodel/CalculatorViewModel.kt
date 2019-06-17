package com.thegreychain.kotlincalculator.viewmodel

import android.arch.lifecycle.ViewModel
import com.thegreychain.kotlincalculator.view.IViewContract
import io.reactivex.BackpressureStrategy
import com.thegreychain.kotlincalculator.domain.domainmodel.Expression

import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class CalculatorViewModel(private val data: Expression
                          = Expression.createSuccessModel(""),
                          private val displayFlowable: BehaviorSubject<String>
                          = BehaviorSubject.create()) : ViewModel(),
        IViewContract.ViewModel {
    override fun getDisplayState(): String {
        return data.result
    }

    override fun getDisplayStatePublisher(): Flowable<String> {
        return displayFlowable.toFlowable(BackpressureStrategy.LATEST)
    }

    /**
     * This method must do two things:
     * 1. persist the UI State of the Application (obviously)
     * 2. Emit that state immediately after (as a Flowable).
     *
     * Step 2 is achieved by a Behaviour Subject. Once the Presenter (Subscriber), calls
     * getDisplayStatePublisher, it is given a special kind of Subject (PublisherSubject disguised
     * as a Flowable) that can emit values when I tell it to (via onNext(getDisplayState())).
     *
     * Step 2 in simpler language: When we give data to the ViewModel, we want the ViewModel to give
     * a copy of that new data to the Presenter instantly. This is better than having the Presenter
     * constantly ask it for the value.
    vi*
     */
    override fun setDisplayState(result: String) {
        this.data.result = result
        displayFlowable.onNext(getDisplayState())
    }
}

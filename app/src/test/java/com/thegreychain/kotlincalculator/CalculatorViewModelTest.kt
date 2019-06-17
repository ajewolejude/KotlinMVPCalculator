package com.thegreychain.kotlincalculator

import com.thegreychain.kotlincalculator.viewmodel.CalculatorViewModel
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertTrue
import org.junit.Test

class CalculatorViewModelTest {


    val STATE = "1234"

    @Test
    fun onSetDisplayState() {
        val viewModel = CalculatorViewModel()

        val subscriber = TestSubscriber<String>()

        viewModel.getDisplayStatePublisher().subscribeWith(subscriber)
        viewModel.setDisplayState(STATE)

        assertTrue(subscriber.values()[0] == STATE)
    }

}
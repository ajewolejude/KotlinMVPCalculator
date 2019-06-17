package com.thegreychain.kotlinclculator

import com.thegreychain.kotlinclculator.viewmodel.CalculatorViewModel
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertTrue
import org.junit.Test

class CalculatorViewModelTest {


    val STATE = "LOLOLOLOLOLOLOLOLOLOL"

    @Test
    fun onSetDisplayState() {
        val viewModel = CalculatorViewModel()

        val subscriber = TestSubscriber<String>()

        viewModel.getDisplayStatePublisher().subscribeWith(subscriber)
        viewModel.setDisplayState(STATE)

        assertTrue(subscriber.values()[0] == STATE)
    }

}
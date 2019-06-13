package com.thegreychain.kotlinclculator.view

import io.reactivex.Flowable

interface IViewContract {

    interface View {
        fun getCurrentExpression(): String
        fun setDisplay(value: String)
        fun showError(value: String)
        fun restartFeature()
    }

    interface ViewModel {
        fun setDisplayState(result: String)

        //Get something (Flowable in this case)
        // which will emit a CalcUIModel as soon as it is set (above method)
        fun getDisplayStatePublisher(): Flowable<String>

        fun getDisplayState():String
    }

    interface Presenter {
        fun onEvaluateClick(expression:String)
        fun onInputButtonClick(value: String)
        fun onDeleteClick()
        fun onLongDeleteClick()
        fun bind()
        fun clear()
    }
}

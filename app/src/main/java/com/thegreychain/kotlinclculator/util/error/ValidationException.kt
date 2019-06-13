package com.thegreychain.kotlinclculator.util.error

class ValidationException: Exception(){
    companion object {
        const val message = "Invalid ExpressionDataModel"
    }
}
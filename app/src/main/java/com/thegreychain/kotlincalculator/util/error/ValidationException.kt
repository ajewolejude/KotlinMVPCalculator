package com.thegreychain.kotlincalculator.util.error

class ValidationException: Exception(){
    companion object {
        const val message = "Invalid Expression Data Model"
    }
}
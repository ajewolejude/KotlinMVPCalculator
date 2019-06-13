package com.thegreychain.kotlinclculator.domain.repository

interface IValidator {

    //This part of the program can operate synchronously
    fun validateExpression(expression:String): Boolean
}
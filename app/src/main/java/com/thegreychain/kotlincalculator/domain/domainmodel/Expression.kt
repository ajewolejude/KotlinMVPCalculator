package com.thegreychain.kotlincalculator.domain.domainmodel

class Expression private constructor(var result: String,
                                     var successful: Boolean) {
    companion object Factory{
        fun createSuccessModel(result: String): Expression {
            return Expression(result,
                    true)
        }

        fun createFailureModel(result: String): Expression {
            return Expression(result,
                    false)
        }
    }
}
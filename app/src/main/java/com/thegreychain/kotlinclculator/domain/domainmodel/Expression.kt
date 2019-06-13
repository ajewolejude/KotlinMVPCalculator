package com.thegreychain.kotlinclculator.domain.domainmodel

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
        //could even do createLoadingModel if appropriate. This App is simple enough that it doesn't need long running operations
    }
}
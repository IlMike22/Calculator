package com.mind.market.calculator.domain

sealed interface ExpressionPart {
    data class Number(val number: Double) : ExpressionPart
    data class Op(val operation: Operation) : ExpressionPart
    data class Parantheses(val type: ParanthesesType) : ExpressionPart

}

sealed interface ParanthesesType {
    object Opening : ParanthesesType
    object Closing : ParanthesesType
}
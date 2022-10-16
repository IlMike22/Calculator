package com.mind.market.calculator.domain

sealed interface CalculationAction {
    data class Number(val number: Int) : CalculationAction
    data class Op(val operation: Operation) : CalculationAction
    object Clear : CalculationAction
    object Delete : CalculationAction
    object Parantheses : CalculationAction
    object Calculate : CalculationAction
    object Decimal : CalculationAction
}
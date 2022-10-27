package com.mind.market.calculator.domain

sealed interface CalculatorAction {
    data class Number(val number: Int) : CalculatorAction
    data class Op(val operation: Operation) : CalculatorAction
    object Clear : CalculatorAction
    object Delete : CalculatorAction
    object Parantheses : CalculatorAction
    object Calculate : CalculatorAction
    object Decimal : CalculatorAction
}
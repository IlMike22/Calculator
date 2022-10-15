package com.mind.market.calculator.domain

class ExpressionWriter {
    var expression = ""
    fun processAction(action: CalculationAction) {
        when (action) {
            CalculationAction.Calculate -> {
                val parser = ExpressionParser(prepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression = evaluator.evaluate().toString()
            }
            CalculationAction.Clear -> {
                expression = ""
            }
            CalculationAction.Decimal -> {
                if (isDecimalValid()) {
                    expression += "."
                }
            }
            CalculationAction.Delete -> {
                expression = expression.dropLast(1)
            }
            is CalculationAction.Number -> {
                expression += action.number
            }
            is CalculationAction.Op -> {
                if (isOperationValid(action.operation)) {
                    expression += action.operation.symbol
                }
            }
            CalculationAction.Parantheses -> {
                processParantheses()
            }
        }
    }

    private fun prepareForCalculation(): String {
        val newExpression = expression.takeLastWhile {
            it in "$operationSymbols(."
        }
        if (newExpression.isEmpty()) {
            return "0"
        }

        return newExpression
    }

    private fun isOperationValid(operation: Operation): Boolean {
        if (operation in listOf(Operation.ADD, Operation.SUBTRACT))
            return expression.isEmpty() || expression.last() in "$operationSymbols()1234567890"
        return expression.isNotEmpty() || expression.last() in "01234567890)"
    }

    private fun isDecimalValid(): Boolean {
        if (expression.isEmpty() || expression.last() in "$operationSymbols.()")
            return false
        return !expression.takeLastWhile { // we do not allow sth like 5.43.22 with this code :)
            it in "01234567890."
        }.contains(".")
    }

    private fun processParantheses() {
        val openingCount = expression.count { it == '(' }
        val closingCount = expression.count { it == ')' }
        expression += when {
            expression.isEmpty() ||
                    expression.last() in "$operationSymbols(" -> "("
            expression.last() in "0123456789)" && openingCount == closingCount -> return
            else -> ")"
        }
    }
}
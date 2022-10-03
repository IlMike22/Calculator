package com.mind.market.calculator.domain

class ExpressionParser(private val calculation: String) {
    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()
        var i = 0

        while (i < calculation.length) {
            val currentChar = calculation[i]
            when {
                currentChar in operationSymbols -> {
                    result.add(ExpressionPart.Op(operationFromSymbol(currentChar)))
                }
                currentChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                currentChar in "()" -> {
                    parseParantheses(currentChar, result)
                }
            }
            i++
        }
        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while (i < calculation.length && calculation[i] in "0123456789.") {
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return i
    }

    private fun parseParantheses(currentChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parantheses(
                type = when (currentChar) {
                    '(' -> ParanthesesType.Opening
                    ')' -> ParanthesesType.Closing
                    else -> throw IllegalArgumentException("unknown parantheses type")
                }
            )
        )
    }
}
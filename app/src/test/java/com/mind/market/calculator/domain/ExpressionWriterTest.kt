package com.mind.market.calculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class ExpressionWriterTest {
    private lateinit var writer: ExpressionWriter

    @Before
    fun setup() {
        writer = ExpressionWriter()
    }

    @Test
    fun `Initial parantheses parsed`() {
        writer.processAction(CalculationAction.Parantheses)
        writer.processAction(CalculationAction.Number(5))
        writer.processAction(CalculationAction.Op(Operation.ADD))
        writer.processAction(CalculationAction.Number(4))
        writer.processAction(CalculationAction.Parantheses)

        assertThat(writer.expression).isEqualTo("(5+4)")
    }

    @Test
    fun `Closing parentheses if start not parsed`() {
        writer.processAction(CalculationAction.Parantheses)
        writer.processAction(CalculationAction.Parantheses)
        assertThat(writer.expression).isEqualTo("((")
    }

    @Test
    fun `Parentheses around a number are parsed`() {
        writer.processAction(CalculationAction.Parantheses)
        writer.processAction(CalculationAction.Number(4))
        writer.processAction(CalculationAction.Parantheses)
        assertThat(writer.expression).isEqualTo("(4)")
    }
}
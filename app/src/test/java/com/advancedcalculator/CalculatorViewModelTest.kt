package com.advancedcalculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun testInitialState() {
        val state = viewModel.state.value
        assertEquals("0", state.display)
        assertEquals("", state.expression)
        assertFalse(state.isError)
    }

    @Test
    fun testDigitInput() {
        viewModel.onDigit("5")
        assertEquals("5", viewModel.state.value.display)
    }

    @Test
    fun testMultipleDigits() {
        viewModel.onDigit("1")
        viewModel.onDigit("2")
        viewModel.onDigit("3")
        assertEquals("123", viewModel.state.value.display)
    }

    @Test
    fun testClear() {
        viewModel.onDigit("5")
        viewModel.onClear()
        assertEquals("0", viewModel.state.value.display)
        assertEquals("", viewModel.state.value.expression)
    }

    @Test
    fun testAddition() {
        viewModel.onDigit("3")
        viewModel.onOperator("+")
        viewModel.onDigit("5")
        viewModel.onEquals()
        assertEquals("8", viewModel.state.value.display)
    }

    @Test
    fun testSubtraction() {
        viewModel.onDigit("1")
        viewModel.onDigit("0")
        viewModel.onOperator("-")
        viewModel.onDigit("4")
        viewModel.onEquals()
        assertEquals("6", viewModel.state.value.display)
    }

    @Test
    fun testMultiplication() {
        viewModel.onDigit("6")
        viewModel.onOperator("×")
        viewModel.onDigit("7")
        viewModel.onEquals()
        assertEquals("42", viewModel.state.value.display)
    }

    @Test
    fun testDivision() {
        viewModel.onDigit("1")
        viewModel.onDigit("5")
        viewModel.onOperator("÷")
        viewModel.onDigit("3")
        viewModel.onEquals()
        assertEquals("5", viewModel.state.value.display)
    }

    @Test
    fun testDecimalInput() {
        viewModel.onDigit("3")
        viewModel.onDecimal()
        viewModel.onDigit("5")
        assertEquals("3.5", viewModel.state.value.display)
    }

    @Test
    fun testHistoryAfterCalculation() {
        viewModel.onDigit("2")
        viewModel.onOperator("+")
        viewModel.onDigit("2")
        viewModel.onEquals()
        assertTrue(viewModel.history.value.isNotEmpty())
        assertEquals("4", viewModel.history.value.first().result)
    }

    @Test
    fun testHistoryLimit() {
        repeat(150) { i ->
            viewModel.onDigit("1")
            viewModel.onOperator("+")
            viewModel.onDigit("1")
            viewModel.onEquals()
        }
        assertTrue(viewModel.history.value.size <= 100)
    }

    @Test
    fun testPercent() {
        viewModel.onDigit("5")
        viewModel.onDigit("0")
        viewModel.onPercent()
        assertEquals("0.5", viewModel.state.value.display)
    }

    @Test
    fun testToggleSign() {
        viewModel.onDigit("5")
        viewModel.onToggleSign()
        assertEquals("-5", viewModel.state.value.display)
        viewModel.onToggleSign()
        assertEquals("5", viewModel.state.value.display)
    }

    @Test
    fun testSquareRoot() {
        viewModel.onDigit("9")
        viewModel.onSquareRoot()
        assertEquals("3", viewModel.state.value.display)
    }

    @Test
    fun testClearOnNewInput() {
        viewModel.onDigit("5")
        viewModel.onOperator("+")
        viewModel.onDigit("3")
        viewModel.onEquals()
        assertEquals("8", viewModel.state.value.display)
        viewModel.onDigit("2")
        assertEquals("2", viewModel.state.value.display)
    }
}

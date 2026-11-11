package com.advancedcalculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ExpressionEvaluatorTest {

    @Test
    fun testAddition() {
        assertEquals(8.0, ExpressionEvaluator.evaluate("3+5"), 0.001)
    }

    @Test
    fun testSubtraction() {
        assertEquals(3.0, ExpressionEvaluator.evaluate("10-7"), 0.001)
    }

    @Test
    fun testMultiplication() {
        assertEquals(42.0, ExpressionEvaluator.evaluate("6*7"), 0.001)
    }

    @Test
    fun testDivision() {
        assertEquals(5.0, ExpressionEvaluator.evaluate("15/3"), 0.001)
    }

    @Test
    fun testDecimal() {
        assertEquals(7.8, ExpressionEvaluator.evaluate("5.5+2.3"), 0.001)
    }

    @Test
    fun testComplexExpression() {
        assertEquals(15.0, ExpressionEvaluator.evaluate("3+4*3"), 0.001)
    }

    @Test
    fun testParentheses() {
        assertEquals(21.0, ExpressionEvaluator.evaluate("(3+4)*3"), 0.001)
    }

    @Test
    fun testNestedParentheses() {
        assertEquals(14.0, ExpressionEvaluator.evaluate("2*(3+4)"), 0.001)
    }

    @Test
    fun testPower() {
        assertEquals(8.0, ExpressionEvaluator.evaluate("2^3"), 0.001)
    }

    @Test
    fun testUnaryMinus() {
        assertEquals(-5.0, ExpressionEvaluator.evaluate("-5"), 0.001)
    }

    @Test
    fun testTrigonometry() {
        val result = ExpressionEvaluator.evaluate("sin(90)")
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun testCosine() {
        assertEquals(0.0, ExpressionEvaluator.evaluate("cos(90)"), 0.001)
    }

    @Test
    fun testTangent() {
        assertEquals(1.0, ExpressionEvaluator.evaluate("tan(45)"), 0.001)
    }

    @Test
    fun testLogarithm() {
        assertEquals(2.0, ExpressionEvaluator.evaluate("log(100)"), 0.001)
    }

    @Test
    fun testNaturalLog() {
        assertEquals(1.0, ExpressionEvaluator.evaluate("ln(2.718281828459045)"), 0.001)
    }

    @Test
    fun testSquareRoot() {
        assertEquals(3.0, ExpressionEvaluator.evaluate("sqrt(9)"), 0.001)
    }

    @Test
    fun testDivisionByZero() {
        assertThrows(ArithmeticException::class.java) {
            ExpressionEvaluator.evaluate("5/0")
        }
    }

    @Test
    fun testPercent() {
        assertEquals(50.0, ExpressionEvaluator.evaluate("100*50%"), 0.001)
    }

    @Test
    fun testChainedOperations() {
        assertEquals(10.0, ExpressionEvaluator.evaluate("1+2+3+4"), 0.001)
    }

    @Test
    fun testMultiplicationPriority() {
        assertEquals(13.0, ExpressionEvaluator.evaluate("1+2*6"), 0.001)
    }

    @Test
    fun testParenthesesPriority() {
        assertEquals(18.0, ExpressionEvaluator.evaluate("(1+2)*6"), 0.001)
    }
}

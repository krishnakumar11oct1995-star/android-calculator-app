package com.advancedcalculator

object ExpressionEvaluator {

    fun evaluate(expression: String): Double {
        val sanitized = expression
            .replace(" ", "")
            .replace("×", "*")
            .replace("÷", "/")
            .replace("π", Math.PI.toString())
            .replace("e", Math.E.toString())
        return parseExpression(sanitized)
    }

    private var index = 0
    private var input = ""

    private fun parseExpression(expr: String): Double {
        input = expr
        index = 0
        val result = parseAddSub()
        if (index < input.length) throw IllegalArgumentException("Unexpected character: ${input[index]}")
        return result
    }

    private fun parseAddSub(): Double {
        var left = parseMulDiv()
        while (index < input.length) {
            when (input[index]) {
                '+' -> { index++; left += parseMulDiv() }
                '-' -> { index++; left -= parseMulDiv() }
                else -> break
            }
        }
        return left
    }

    private fun parseMulDiv(): Double {
        var left = parsePower()
        while (index < input.length) {
            when (input[index]) {
                '*' -> { index++; left *= parsePower() }
                '/' -> {
                    index++
                    val divisor = parsePower()
                    if (divisor == 0.0) throw ArithmeticException("Division by zero")
                    left /= divisor
                }
                else -> break
            }
        }
        return left
    }

    private fun parsePower(): Double {
        var left = parseUnary()
        if (index < input.length && input[index] == '^') {
            index++
            val right = parseUnary()
            left = Math.pow(left, right)
        }
        return left
    }

    private fun parseUnary(): Double {
        if (index >= input.length) throw IllegalArgumentException("Unexpected end of expression")
        when (input[index]) {
            '+' -> { index++; return parseUnary() }
            '-' -> { index++; return -parseUnary() }
            '√' -> { index++; return Math.sqrt(parseUnary()) }
        }
        return parseAtom()
    }

    private fun parseAtom(): Double {
        if (index >= input.length) throw IllegalArgumentException("Unexpected end of expression")
        var value: Double
        when {
            input[index] == '(' -> {
                index++
                value = parseAddSub()
                if (index >= input.length || input[index] != ')') throw IllegalArgumentException("Missing closing parenthesis")
                index++
            }
            input[index].isDigit() || input[index] == '.' -> value = parseNumber()
            input.startsWith("sin(", index) -> { index += 4; value = parseAddSub(); expect(')'); value = Math.sin(Math.toRadians(value)) }
            input.startsWith("cos(", index) -> { index += 4; value = parseAddSub(); expect(')'); value = Math.cos(Math.toRadians(value)) }
            input.startsWith("tan(", index) -> { index += 4; value = parseAddSub(); expect(')'); value = Math.tan(Math.toRadians(value)) }
            input.startsWith("log(", index) -> { index += 4; value = parseAddSub(); expect(')'); value = Math.log10(value) }
            input.startsWith("ln(", index) -> { index += 3; value = parseAddSub(); expect(')'); value = Math.log(value) }
            input.startsWith("sqrt(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.sqrt(value) }
            input.startsWith("abs(", index) -> { index += 4; value = parseAddSub(); expect(')'); value = Math.abs(value) }
            input.startsWith("asin(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.toDegrees(Math.asin(value)) }
            input.startsWith("acos(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.toDegrees(Math.acos(value)) }
            input.startsWith("atan(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.toDegrees(Math.atan(value)) }
            input.startsWith("sinh(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.sinh(value) }
            input.startsWith("cosh(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.cosh(value) }
            input.startsWith("tanh(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.tanh(value) }
            input.startsWith("floor(", index) -> { index += 6; value = parseAddSub(); expect(')'); value = Math.floor(value) }
            input.startsWith("ceil(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = Math.ceil(value) }
            input.startsWith("fact(", index) -> { index += 5; value = parseAddSub(); expect(')'); value = factorial(value.toLong()).toDouble() }
            else -> throw IllegalArgumentException("Unexpected character: ${input[index]}")
        }
        if (index < input.length && input[index] == '%') {
            index++
            return value / 100.0
        }
        return value
    }

    private fun expect(c: Char) {
        if (index >= input.length || input[index] != c) throw IllegalArgumentException("Expected '$c'")
        index++
    }

    private fun parseNumber(): Double {
        val start = index
        while (index < input.length && (input[index].isDigit() || input[index] == '.')) index++
        return input.substring(start, index).toDouble()
    }

    private fun factorial(n: Long): Long {
        if (n < 0) throw IllegalArgumentException("Factorial of negative number")
        var result = 1L
        for (i in 2..n) result *= i
        return result
    }
}
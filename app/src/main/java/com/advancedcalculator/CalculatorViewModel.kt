package com.advancedcalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalculationState(
    val expression: String = "",
    val display: String = "0",
    val result: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)

data class HistoryEntry(
    val expression: String,
    val result: String,
    val tabType: String
)

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculationState())
    val state: StateFlow<CalculationState> = _state.asStateFlow()

    private val _history = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val history: StateFlow<List<HistoryEntry>> = _history.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _clearOnNextInput = MutableStateFlow(false)
    val clearOnNextInput: StateFlow<Boolean> = _clearOnNextInput.asStateFlow()

    private var lastResult: Double? = null
    private var storedValue: Double? = null

    fun selectTab(index: Int) { _selectedTab.value = index }

    fun onDigit(digit: String) {
        val current = _state.value
        if (current.isError) {
            _state.value = current.copy(expression = "", display = "0", result = "", isError = false, errorMessage = "")
        }
        if (_clearOnNextInput.value) {
            _clearOnNextInput.value = false
            _state.value = current.copy(expression = "", display = digit, result = "")
            return
        }
        val currentDisplay = if (current.display == "0" && digit != ".") "" else current.display
        _state.value = current.copy(display = currentDisplay + digit)
    }

    fun onDecimal() {
        val current = _state.value
        if (!current.display.contains(".")) {
            _state.value = current.copy(display = current.display + ".")
        }
    }

    fun onOperator(op: String) {
        val current = _state.value
        var expr = current.expression
        if (current.display.isNotEmpty() && current.display != "0") {
            expr = if (expr.isNotEmpty()) "$expr ${current.display}" else current.display
        }
        expr = "$expr $op"
        _state.value = current.copy(
            expression = expr,
            display = "",
            result = ""
        )
        _clearOnNextInput.value = false
    }

    fun onFunction(func: String) {
        val current = _state.value
        val display = if (current.display == "0") "" else current.display
        _state.value = current.copy(
            expression = display,
            display = "$func(",
            result = ""
        )
        _clearOnNextInput.value = false
    }

    fun onConstant(constant: String) {
        val current = _state.value
        when (constant) {
            "π" -> _state.value = current.copy(display = Math.PI.toString())
            "e" -> _state.value = current.copy(display = Math.E.toString())
        }
    }

    fun onEquals() {
        calculateResult()
    }

    fun onClear() {
        _state.value = CalculationState()
        lastResult = null
    }

    fun onDelete() {
        val current = _state.value
        if (current.display.isNotEmpty() && current.display != "0") {
            _state.value = current.copy(display = current.display.dropLast(1).ifEmpty { "0" })
        }
    }

    fun onMemoryStore() {
        try {
            val value = _state.value.display.toDouble()
            storedValue = value
        } catch (_: Exception) {}
    }

    fun onMemoryRecall() {
        if (storedValue != null) {
            _state.value = _state.value.copy(display = storedValue.toString())
        }
    }

    fun onMemoryClear() { storedValue = null }

    fun onMemoryAdd() {
        try {
            storedValue = (storedValue ?: 0.0) + _state.value.display.toDouble()
        } catch (_: Exception) {}
    }

    fun onMemorySubtract() {
        try {
            storedValue = (storedValue ?: 0.0) - _state.value.display.toDouble()
        } catch (_: Exception) {}
    }

    fun onAns() {
        if (lastResult != null) {
            _state.value = _state.value.copy(display = lastResult.toString())
        }
    }

    fun onParenthesis(char: String) {
        val current = _state.value
        if (current.display == "0" || current.display.isEmpty()) {
            _state.value = current.copy(display = char)
        } else {
            _state.value = current.copy(display = current.display + char)
        }
    }

    fun onPower() {
        val current = _state.value
        if (current.display.isNotEmpty() && current.display != "0") {
            _state.value = current.copy(
                expression = current.display,
                display = "",
                result = ""
            )
        }
    }

    fun onPercent() {
        val current = _state.value
        try {
            val value = current.display.toDouble() / 100.0
            _state.value = current.copy(display = formatDouble(value))
        } catch (_: Exception) {}
    }

    fun onSquareRoot() {
        val current = _state.value
        try {
            val value = Math.sqrt(current.display.toDouble())
            _state.value = current.copy(display = formatDouble(value))
        } catch (_: Exception) {
            _state.value = current.copy(isError = true, errorMessage = "Invalid input for sqrt")
        }
    }

    fun onToggleSign() {
        val current = _state.value
        if (current.display != "0") {
            if (current.display.startsWith("-")) {
                _state.value = current.copy(display = current.display.drop(1))
            } else {
                _state.value = current.copy(display = "-${current.display}")
            }
        }
    }

    private fun calculateResult() {
        val current = _state.value
        var expr = current.expression
        if (current.display.isNotEmpty() && current.display != "0") {
            expr = if (expr.isNotEmpty()) "$expr ${current.display}" else current.display
        }
        if (expr.isEmpty()) return
        try {
            val result = ExpressionEvaluator.evaluate(expr)
            val formatted = formatDouble(result)
            val entry = HistoryEntry(expression = expr, result = formatted, tabType = getTabName())
            val updatedHistory = listOf(entry) + _history.value.take(99)
            _state.value = current.copy(
                expression = "",
                display = formatted,
                result = formatted,
                isError = false,
                errorMessage = ""
            )
            _history.value = updatedHistory
            _clearOnNextInput.value = true
            lastResult = result
        } catch (e: Exception) {
            _state.value = current.copy(isError = true, errorMessage = e.message ?: "Error")
        }
    }

    fun getFormattedExpression(): String {
        val current = _state.value
        return if (current.expression.isNotEmpty() && current.display.isNotEmpty() && current.display != "0") {
            "${current.expression} ${current.display}"
        } else if (current.expression.isNotEmpty()) {
            current.expression
        } else {
            current.display
        }
    }

    private fun getTabName(): String = when (_selectedTab.value) {
        0 -> "Basic"
        1 -> "Scientific"
        2 -> "Graphing"
        3 -> "Converter"
        else -> "Basic"
    }

    private fun formatDouble(value: Double): String {
        if (value == Math.floor(value) && !value.isInfinite()) {
            return value.toLong().toString()
        }
        val s = String.format("%.10f", value).trimEnd('0').trimEnd('.')
        return s
    }
}

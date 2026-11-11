package com.advancedcalculator.ui.calculator

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScientificCalculatorTab(
    display: String,
    expression: String,
    isError: Boolean,
    onDigit: (String) -> Unit,
    onOperator: (String) -> Unit,
    onEquals: () -> Unit,
    onClear: () -> Unit,
    onFunction: (String) -> Unit,
    onConstant: (String) -> Unit,
    onParenthesis: (String) -> Unit,
    onPower: () -> Unit,
    onSquareRoot: () -> Unit,
    onAns: () -> Unit,
    onMemoryStore: () -> Unit,
    onMemoryRecall: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySubtract: () -> Unit,
    onToggleSign: () -> Unit,
    onPercent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        CalculatorDisplay(expression = expression, display = display, isError = isError)
        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MiniButton("sin", onClick = { onFunction("sin") })
                MiniButton("cos", onClick = { onFunction("cos") })
                MiniButton("tan", onClick = { onFunction("tan") })
                MiniButton("log", onClick = { onFunction("log") })
                MiniButton("ln", onClick = { onFunction("ln") })
                MiniButton("√", onClick = onSquareRoot)
                MiniButton("π", onClick = { onConstant("π") })
                MiniButton("e", onClick = { onConstant("e") })
                MiniButton("x^y", onClick = onPower)
                MiniButton("ANS", onClick = onAns)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                MiniButton("(", onClick = { onParenthesis("(") })
                MiniButton(")", onClick = { onParenthesis(")") })
                MiniButton("MC", onClick = { onMemoryStore() }) // Memory Clear
                MiniButton("MR", onClick = { onMemoryRecall() }) // Memory Recall
                MiniButton("M+", onClick = { onMemoryAdd() }) // Memory Add
                MiniButton("M-", onClick = { onMemorySubtract() }) // Memory Subtract
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                SciButton("C", onClick = onClear, color = ButtonColor.Special)
                SciButton("±", onClick = { onToggleSign() }, color = ButtonColor.Special)
                SciButton("%", onClick = { onPercent() }, color = ButtonColor.Special)
                SciButton("÷", onClick = { onOperator("÷") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                SciButton("7", onClick = { onDigit("7") })
                SciButton("8", onClick = { onDigit("8") })
                SciButton("9", onClick = { onDigit("9") })
                SciButton("×", onClick = { onOperator("×") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                SciButton("4", onClick = { onDigit("4") })
                SciButton("5", onClick = { onDigit("5") })
                SciButton("6", onClick = { onDigit("6") })
                SciButton("-", onClick = { onOperator("-") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                SciButton("1", onClick = { onDigit("1") })
                SciButton("2", onClick = { onDigit("2") })
                SciButton("3", onClick = { onDigit("3") })
                SciButton("+", onClick = { onOperator("+") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                SciButton("0", onClick = { onDigit("0") })
                SciButton(".", onClick = { onDigit(".") })
                SciButton("Ans", onClick = onAns)
                SciButton("=", onClick = onEquals, color = ButtonColor.Equals)
            }
        }
    }
}

@Composable
private fun MiniButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(32.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun RowScope.SciButton(
    text: String,
    onClick: () -> Unit,
    color: ButtonColor = ButtonColor.Numeric
) {
    val containerColor = when (color) {
        ButtonColor.Operator -> MaterialTheme.colorScheme.secondaryContainer
        ButtonColor.Special -> MaterialTheme.colorScheme.surfaceVariant
        ButtonColor.Equals -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when (color) {
        ButtonColor.Equals -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f).height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
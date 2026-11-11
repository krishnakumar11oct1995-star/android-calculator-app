package com.advancedcalculator.ui.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorDisplay(
    expression: String,
    display: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = expression,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isError) "Error" else display,
            fontSize = if (display.length > 12) 28.sp else 36.sp,
            fontWeight = FontWeight.Bold,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CalcButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Numeric,
    span: Int = 1
) {
    val containerColor = when (color) {
        ButtonColor.Operator -> MaterialTheme.colorScheme.secondary
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
        modifier = modifier
            .fillMaxWidth()
            .height(if (span > 1) 64.dp else 56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = if (text.length > 2) 14.sp else 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

enum class ButtonColor { Numeric, Operator, Special, Equals }

@Composable
fun BasicCalculatorTab(
    display: String,
    expression: String,
    isError: Boolean,
    onDigit: (String) -> Unit,
    onOperator: (String) -> Unit,
    onEquals: () -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onDecimal: () -> Unit,
    onPercent: () -> Unit,
    onToggleSign: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        CalculatorDisplay(
            expression = expression,
            display = display,
            isError = isError
        )

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                CalcButton("C", onClick = onClear, color = ButtonColor.Special)
                CalcButton("±", onClick = onToggleSign, color = ButtonColor.Special)
                CalcButton("%", onClick = onPercent, color = ButtonColor.Special)
                CalcButton("÷", onClick = { onOperator("÷") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                CalcButton("7", onClick = { onDigit("7") })
                CalcButton("8", onClick = { onDigit("8") })
                CalcButton("9", onClick = { onDigit("9") })
                CalcButton("×", onClick = { onOperator("×") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                CalcButton("4", onClick = { onDigit("4") })
                CalcButton("5", onClick = { onDigit("5") })
                CalcButton("6", onClick = { onDigit("6") })
                CalcButton("-", onClick = { onOperator("-") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                CalcButton("1", onClick = { onDigit("1") })
                CalcButton("2", onClick = { onDigit("2") })
                CalcButton("3", onClick = { onDigit("3") })
                CalcButton("+", onClick = { onOperator("+") }, color = ButtonColor.Operator)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                CalcButton("0", onClick = { onDigit("0") }, span = 2, modifier = Modifier.weight(2f))
                CalcButton(".", onClick = onDecimal)
                CalcButton("=", onClick = onEquals, color = ButtonColor.Equals)
            }
        }
    }
}

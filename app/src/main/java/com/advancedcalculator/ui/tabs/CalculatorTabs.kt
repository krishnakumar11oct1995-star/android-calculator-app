package com.advancedcalculator.ui.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.advancedcalculator.CalculatorViewModel
import com.advancedcalculator.ui.calculator.BasicCalculatorTab
import com.advancedcalculator.ui.calculator.ScientificCalculatorTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorTabs(viewModel: CalculatorViewModel) {
    val state by viewModel.state.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val tabs = listOf("Basic", "Scientific", "Graphing", "Converter")

    Scaffold(
        topBar = {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { viewModel.selectTab(index) },
                        text = { Text(title, style = MaterialTheme.typography.labelLarge) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (selectedTab) {
                0 -> BasicCalculatorTab(
                    display = state.display,
                    expression = viewModel.getFormattedExpression(),
                    isError = state.isError,
                    onDigit = { viewModel.onDigit(it) },
                    onOperator = { viewModel.onOperator(it) },
                    onEquals = { viewModel.onEquals() },
                    onClear = { viewModel.onClear() },
                    onDelete = { viewModel.onDelete() },
                    onDecimal = { viewModel.onDecimal() },
                    onPercent = { viewModel.onPercent() },
                    onToggleSign = { viewModel.onToggleSign() }
                )
                1 -> ScientificCalculatorTab(
                    display = state.display,
                    expression = viewModel.getFormattedExpression(),
                    isError = state.isError,
                    onDigit = { viewModel.onDigit(it) },
                    onOperator = { viewModel.onOperator(it) },
                    onEquals = { viewModel.onEquals() },
                    onClear = { viewModel.onClear() },
                    onFunction = { viewModel.onFunction(it) },
                    onConstant = { viewModel.onConstant(it) },
                    onParenthesis = { viewModel.onParenthesis(it) },
                    onPower = { viewModel.onPower() },
                    onSquareRoot = { viewModel.onSquareRoot() },
                    onAns = { viewModel.onAns() },
                    onMemoryStore = { viewModel.onMemoryStore() },
                    onMemoryRecall = { viewModel.onMemoryRecall() },
                    onMemoryAdd = { viewModel.onMemoryAdd() },
                    onMemorySubtract = { viewModel.onMemorySubtract() },
                    onToggleSign = { viewModel.onToggleSign() },
                    onPercent = { viewModel.onPercent() }
                )
                2 -> placeHolderTab("Graphing - Coming Soon")
                3 -> placeHolderTab("Converter - Coming Soon")
            }
        }
    }
}

@Composable
private fun placeHolderTab(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
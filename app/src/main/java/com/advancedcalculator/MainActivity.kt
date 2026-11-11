package com.advancedcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.advancedcalculator.ui.tabs.CalculatorTabs
import com.advancedcalculator.ui.theme.AdvancedCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedCalculatorTheme {
                val viewModel: CalculatorViewModel = viewModel()
                CalculatorTabs(viewModel = viewModel)
            }
        }
    }
}

package com.sartorio.showcase.calculator

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val lastOperation: String = ""
)
package com.sartorio.showcase.calculator

sealed class CalculatorOperation(val symbol : String){
    object  Add : CalculatorOperation("+")
    object  Subtract : CalculatorOperation("-")
    object  Multiply : CalculatorOperation("x")
    object  Divide : CalculatorOperation("/")
}

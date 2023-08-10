package com.sartorio.showcase.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {


    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.Calculate -> {
                if (state.operation != null) {
                    state = CalculatorState(
                        number1 = when (state.operation) {
                            CalculatorOperation.Add -> (state.number1.toBigDecimal()
                                .plus(state.number2.toBigDecimal())).toString()

                            CalculatorOperation.Divide -> (state.number1.toBigDecimal()
                                .div(state.number2.toBigDecimal())).toString()

                            CalculatorOperation.Multiply -> (state.number1.toBigDecimal()
                                .times(state.number2.toBigDecimal())).toString()

                            CalculatorOperation.Subtract -> (state.number1.toBigDecimal()
                                .minus(state.number2.toBigDecimal())).toString()

                            null -> throw Exception("Already checked for null")
                        },
                        lastOperation = state.number1 + (state.operation?.symbol
                            ?: "") + state.number2 + "="
                    )
                }

            }

            CalculatorAction.Clear -> {
                state = CalculatorState()
            }

            CalculatorAction.Decimal -> {
                state = if (state.operation != null) {
                    if (state.number2.contains(".")) return
                    state.copy(number2 = state.number2 + ".")
                } else {
                    if (state.number1.contains(".")) return
                    state.copy(number1 = state.number1 + ".")
                }
            }

            CalculatorAction.Delete -> {
                state = if (state.operation != null) {
                    if (state.number2.isNotBlank()) {
                        state.copy(number2 = state.number2.dropLast(1))

                    } else {
                        state.copy(operation = null)
                    }
                } else {
                    state.copy(number1 = state.number1.dropLast(1))
                }
            }

            is CalculatorAction.Number -> {
                state = if (state.operation != null) {
                    state.copy(number2 = state.number2.addDigit(action.number))
                } else {
                    state.copy(number1 = state.number1.addDigit(action.number))
                }
            }

            is CalculatorAction.Operation -> {
                if (state.number1.isNotBlank()) {
                    state = state.copy(operation = action.operation)
                }
            }
        }
    }

    private fun String.addDigit(digit: Int): String {
        var temp = this + digit.toString()
        while (temp.length > 1 && temp[0] == '0') {
            temp = temp.drop(1)
        }
        return temp
    }

}
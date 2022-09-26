package com.ngengeapps.gifmomo.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import kotlin.math.max


/**
 * Adapter
 */

class ThousandSeparatorOffsetMapping(
    val originalIntegerLength: Int) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int =
        when (offset) {
            0, 1, 2 -> 4
            else -> offset + 1 + calculateThousandsSeparatorCount(originalIntegerLength)
        }

    override fun transformedToOriginal(offset: Int): Int =
        originalIntegerLength +
                calculateThousandsSeparatorCount(originalIntegerLength) +
                2
    private fun calculateThousandsSeparatorCount(
        intDigitCount: Int
    ) = max((intDigitCount - 1) / 3, 0)

    class MoneyVisualTransformation():VisualTransformation{
        override fun filter(text: AnnotatedString): TransformedText {

            val inputText = text.text
            val intPart = if (inputText.length > 2) {
                inputText.subSequence(0, inputText.length - 2)
            } else {
                "0"
            }


            val symbols = DecimalFormat().decimalFormatSymbols
            val thousandsSeparator = symbols.groupingSeparator
            val decimalSeparator = symbols.decimalSeparator

            var fractionPart = if (inputText.length >= 2) {
                inputText.subSequence(inputText.length - 2, inputText.length)
            } else {
                inputText
            }
// Add zeros if the fraction part length is not 2
            if (fractionPart.length < 2) {
                fractionPart = fractionPart.padStart(2, '0')
            }

            val thousandsReplacementPattern = Regex("\\B(?=(?:\\d{3})+(?!\\d))")
            val formattedIntWithThousandsSeparator =
                intPart.replace(
                    thousandsReplacementPattern,
                    thousandsSeparator.toString()
                )
            val newText = AnnotatedString(
                formattedIntWithThousandsSeparator + decimalSeparator + fractionPart,
                text.spanStyles,
                text.paragraphStyles
            )
            val offsetMapping = ThousandSeparatorOffsetMapping(
                originalIntegerLength = intPart.length
            )
            return TransformedText(newText, offsetMapping)
        }

    }
}
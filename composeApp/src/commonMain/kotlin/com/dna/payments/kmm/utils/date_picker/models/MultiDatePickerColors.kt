package com.dna.payments.kmm.utils.date_picker.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.presentation.theme.darkPurple
import com.dna.payments.kmm.presentation.theme.green3
import com.dna.payments.kmm.presentation.theme.greyColorAlpha
import com.dna.payments.kmm.presentation.theme.labelColor
import com.dna.payments.kmm.presentation.theme.white

data class MultiDatePickerColors(
    val cardColor: Color,
    val monthColor: Color,
    val iconColor: Color,
    val weekDayColor: Color,
    val dayNumberColor: Color,
    val disableDayColor: Color,
    val selectedDayNumberColor: Color,
    val selectedIndicatorColor: Color,
    val selectedDayBackgroundColor: Color,
) {
    companion object {
        @Composable
        fun defaults() = MultiDatePickerColors(
            cardColor = MaterialTheme.colorScheme.surface,
            monthColor = MaterialTheme.colorScheme.primary,
            iconColor = MaterialTheme.colorScheme.primary,
            weekDayColor = MaterialTheme.colorScheme.onSurface,
            dayNumberColor = MaterialTheme.colorScheme.primary,
            disableDayColor = MaterialTheme.colorScheme.secondary,
            selectedDayNumberColor = MaterialTheme.colorScheme.onPrimary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
            selectedDayBackgroundColor = MaterialTheme.colorScheme.onPrimary,
        )

        @Composable
        fun defaultsDNA() = MultiDatePickerColors(
            cardColor = white,
            monthColor = green3,
            iconColor = green3,
            weekDayColor = labelColor,
            dayNumberColor = darkPurple,
            disableDayColor = greyColorAlpha,
            selectedDayNumberColor = white,
            selectedIndicatorColor = green3,
            selectedDayBackgroundColor = green3,
        )
    }
}

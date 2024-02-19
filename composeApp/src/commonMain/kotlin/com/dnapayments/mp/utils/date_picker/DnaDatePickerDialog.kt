package com.dnapayments.mp.utils.date_picker

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import korlibs.time.Date
import korlibs.time.DateTime

@Composable
fun DnaDatePickerDialog(
    minDate: Date? = null,
    maxDate: Date? = null,
    singleDatePicker: Boolean = false,
    showDatePicker: Boolean = false,
    onDateSelected: (DateTime) -> Unit = { _ -> },
    onRangeDateSelected: (DateTime, DateTime) -> Unit = { _, _ -> },
    onDismiss: () -> Unit = { }
) {
    if (showDatePicker) {
        Dialog(onDismissRequest = onDismiss) {
            DnaDatePicker(
                minDate = minDate,
                maxDate = maxDate,
                singleDatePicker = singleDatePicker,
                onDateSelected = onDateSelected,
                onRangeDateSelected = onRangeDateSelected
            )
        }
    }
}
package com.dna.payments.kmm.domain.interactors.use_cases.date_picker

import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.date_picker.Menu.ONLINE_PAYMENTS
import com.dna.payments.kmm.domain.model.date_picker.Menu.OVERVIEW
import com.dna.payments.kmm.domain.model.date_picker.Menu.PAYMENT_LINKS
import com.dna.payments.kmm.domain.model.date_picker.Menu.POS_PAYMENTS
import com.dna.payments.kmm.domain.model.date_picker.Menu.REPORTS
import com.dna.payments.kmm.domain.model.date_picker.Menu.SETTLEMENTS

class GetDateRangeUseCase(
    private val dateHelper: DateHelper
) {
    operator fun invoke(menu: Menu): Pair<DatePickerPeriod, DateSelection> =
        Pair(getDatePickerPeriod(menu), getDateSelection(menu))

    operator fun invoke(datePickerPeriod: DatePickerPeriod): DateSelection =
        dateHelper.getDateSelectionByDatePickerPeriod(datePickerPeriod)

    private fun getDatePickerPeriod(menu: Menu): DatePickerPeriod = when (menu) {
        OVERVIEW -> DatePickerPeriod.TODAY
        POS_PAYMENTS -> DatePickerPeriod.TODAY
        ONLINE_PAYMENTS -> DatePickerPeriod.TODAY
        PAYMENT_LINKS -> DatePickerPeriod.PREVIOUS_WEEK
        SETTLEMENTS -> DatePickerPeriod.YESTERDAY
        REPORTS -> DatePickerPeriod.TODAY
    }

    private fun getDateSelection(menu: Menu): DateSelection =
        dateHelper.getDateSelectionByDatePickerPeriod(getDatePickerPeriod(menu))

}
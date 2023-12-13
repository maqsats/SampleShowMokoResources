package com.dna.payments.kmm.domain.interactors.use_cases.date_picker

import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.ADD_SEVEN_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.ADD_THIRTY_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.NINETY_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.SEVEN_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.SIXTY_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.THIRTY_DAYS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.TWELVE_MONTHS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.NewLinkDatePeriodOption.CUSTOM
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.NewLinkDatePeriodOption.FORTY_EIGHT_HOURS
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.NewLinkDatePeriodOption.ONE_MONTH
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.NewLinkDatePeriodOption.ONE_WEEK
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.NewLinkDatePeriodOption.TWENTY_FOUR_HOURS
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.CURRENT_MONTH
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_12_MONTHS
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_30_DAYS
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_60_DAYS
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_90_DAYS
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_MONTH
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.LAST_WEEK
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.PREVIOUS_WEEK
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.THIS_WEEK
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.THIS_YEAR
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.TODAY
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod.YESTERDAY
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.date_picker.IntervalType.DAY
import com.dna.payments.kmm.domain.model.date_picker.IntervalType.HOUR
import com.dna.payments.kmm.domain.model.date_picker.IntervalType.MONTH
import com.dna.payments.kmm.domain.model.date_picker.IntervalType.WEEK
import com.dna.payments.kmm.domain.model.date_picker.IntervalType.YEAR
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.date_picker.Menu.SETTLEMENTS
import com.dna.payments.kmm.utils.extension.isSameDayAs
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan


class DatePeriodUseCase {

    fun findIntervalType(dateSelection: DateSelection): IntervalType {
        val startDate = dateSelection.startDate
        val endDate = dateSelection.endDate

        if (startDate == null || endDate == null) return DAY

        val daysDifference = daysBetween(startDate, endDate)
        val monthsDifference = monthsBetween(startDate, endDate)
        val yearsDifference = yearsBetween(startDate, endDate)

        return when {
            startDate == endDate && startDate == getCurrentDay() -> HOUR
            daysDifference in 1..30 -> DAY
            monthsDifference in 0..2 -> WEEK
            monthsDifference in 2..11 -> MONTH
            yearsDifference >= 1 -> YEAR
            else -> DAY
        }
    }

    fun findDateRange(dateSelection: DateSelection, menu: Menu): DatePickerPeriod {
        val startDate = dateSelection.startDate
        val endDate = dateSelection.endDate


        return when {
            startDate.isSameDayAs(getCurrentDay()) && endDate.isSameDayAs(getEndOfToday()) -> TODAY
            startDate.isSameDayAs(getYesterdayStart()) && endDate.isSameDayAs(getYesterdayEnd()) -> YESTERDAY
            startDate.isSameDayAs(getFirstDayOfWeek()) && endDate.isSameDayAs(getEndOfToday()) -> THIS_WEEK
            startDate.isSameDayAs(getSevenDayEarlier()) && endDate.isSameDayAs(getEndOfToday()) -> if (menu == SETTLEMENTS) LAST_WEEK else PREVIOUS_WEEK
            startDate.isSameDayAs(getFirstDayOfMonth()) && endDate.isSameDayAs(getEndOfToday()) -> CURRENT_MONTH
            startDate.isSameDayAs(getThirtyDayEarlier()) && endDate.isSameDayAs(getEndOfToday()) -> if (menu == SETTLEMENTS) LAST_MONTH else LAST_30_DAYS
            startDate.isSameDayAs(getSixtyDayEarlier()) && endDate.isSameDayAs(getEndOfToday()) -> LAST_60_DAYS
            startDate.isSameDayAs(getNinetyDayEarlier()) && endDate.isSameDayAs(getEndOfToday()) -> LAST_90_DAYS
            startDate.isSameDayAs(getFirstDayOfYear()) && endDate.isSameDayAs(getEndOfToday()) -> THIS_YEAR
            startDate.isSameDayAs(getTwelveMonthEarlier()) && endDate.isSameDayAs(getEndOfToday()) -> LAST_12_MONTHS
            else -> TODAY
        }
    }

    fun getDateSelectionByDatePickerPeriod(datePickerPeriod: DatePickerPeriod): DateSelection {
        val today = getEndOfToday()

        return when (datePickerPeriod) {
            CURRENT_MONTH -> DateSelection(getFirstDayOfMonth(), today)
            PREVIOUS_WEEK -> DateSelection(getSevenDayEarlier(), today)
            TODAY -> DateSelection(getStartOfToday(), today)
            YESTERDAY -> DateSelection(getYesterdayStart(), getYesterdayEnd())
            THIS_WEEK -> DateSelection(getFirstDayOfWeek(), today)
            LAST_30_DAYS -> DateSelection(getThirtyDayEarlier(), today)
            LAST_90_DAYS -> DateSelection(getNinetyDayEarlier(), today)
            THIS_YEAR -> DateSelection(getFirstDayOfYear(), today)
            LAST_12_MONTHS -> DateSelection(getTwelveMonthEarlier(), today)
            LAST_60_DAYS -> DateSelection(getSixtyDayEarlier(), today)
            LAST_WEEK -> DateSelection(getSevenDayEarlier(), today)
            LAST_MONTH -> DateSelection(getThirtyDayEarlier(), today)
        }
    }

    fun getDateNewLinkPeriod(newLinkDatePeriodOption: NewLinkDatePeriodOption): DateTime? =
        when (newLinkDatePeriodOption) {
            ONE_WEEK -> getSevenDayAfter()
            ONE_MONTH -> getThirtyDayAfter()
            FORTY_EIGHT_HOURS -> getFortyEightHourAfter()
            TWENTY_FOUR_HOURS -> getTomorrowEnd()
            CUSTOM -> null
        }

    fun findDate(date: DateTime?): NewLinkDatePeriodOption = when (date) {
        getSevenDayAfter() -> ONE_WEEK
        getThirtyDayAfter() -> ONE_MONTH
        getFortyEightHourAfter() -> FORTY_EIGHT_HOURS
        getTomorrowEnd() -> TWENTY_FOUR_HOURS
        else -> CUSTOM
    }


    private fun getStartOfToday(): DateTime {
        return DateTime.now().dateDayStart
    }


    private fun getFirstDayOfMonth(): DateTime {
        return DateTime.now().startOfMonth.startOfDay
    }

    private fun getFirstDayOfYear(): DateTime {
        return DateTime.now().startOfYear.startOfDay
    }

    private fun getSevenDayEarlier(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = SEVEN_DAYS)).startOfDay
    }

    private fun getSevenDayAfter(): DateTime {
        return DateTime.now().plus(DateTimeSpan(days = ADD_SEVEN_DAYS)).endOfDay
    }

    private fun getThirtyDayEarlier(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = THIRTY_DAYS)).startOfDay
    }

    private fun getThirtyDayAfter(): DateTime {
        return DateTime.now().plus(DateTimeSpan(days = ADD_THIRTY_DAYS)).endOfDay
    }

    private fun getNinetyDayEarlier(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = NINETY_DAYS)).startOfDay
    }

    private fun getSixtyDayEarlier(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = SIXTY_DAYS)).startOfDay
    }

    private fun getTwelveMonthEarlier(): DateTime {
        return DateTime.now().minus(DateTimeSpan(months = TWELVE_MONTHS)).startOfDay
    }

    private fun getYesterdayStart(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = 1)).startOfDay
    }

    private fun getYesterdayEnd(): DateTime {
        return DateTime.now().minus(DateTimeSpan(days = 1)).endOfDay
    }

    private fun getTomorrowEnd(): DateTime {
        return DateTime.now().plus(DateTimeSpan(days = 1)).endOfDay
    }

    private fun getFortyEightHourAfter(): DateTime {
        return DateTime.now().plus(DateTimeSpan(hours = 48)).endOfDay
    }

    private fun getFirstDayOfWeek(): DateTime {
        return DateTime.now().startOfWeek.startOfDay
    }

    private fun getCurrentDay(): DateTime {
        return DateTime.now()
    }

    private fun getEndOfToday(): DateTime {
        return DateTime.now().endOfDay
    }


    private fun daysBetween(startDate: DateTime, endDate: DateTime): Long {
        return (endDate - startDate).days.toLong()
    }

    private fun monthsBetween(startDate: DateTime, endDate: DateTime): Int {
        val diffYear = endDate.year - startDate.year
        val diffMonth = endDate.month1 - startDate.month1
        return diffYear * 12 + diffMonth
    }

    private fun yearsBetween(startDate: DateTime, endDate: DateTime): Int {
        return endDate.year - startDate.year
    }
}
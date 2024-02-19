package com.dnapayments.mp.utils.date_picker

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.date_picker.DatePickerAmPm
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.greyColorAlpha
import com.dnapayments.mp.presentation.theme.poppinsFontFamily
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.utils.date_picker.MultiDatePickerState.SHOWING_CALENDAR
import com.dnapayments.mp.utils.date_picker.MultiDatePickerState.SHOWING_TIME_PICKER
import com.dnapayments.mp.utils.date_picker.MultiDatePickerState.SHOWING_YEAR_PICKER
import com.dnapayments.mp.utils.date_picker.models.MultiDatePickerColors
import com.dnapayments.mp.utils.date_picker.utils.Operation
import com.dnapayments.mp.utils.date_picker.utils.extensions.isAfter
import com.dnapayments.mp.utils.date_picker.utils.extensions.isBefore
import com.dnapayments.mp.utils.date_picker.utils.extensions.isToday
import com.dnapayments.mp.utils.date_picker.utils.extensions.set
import com.dnapayments.mp.utils.date_picker.utils.extensions.toMonthYear
import com.dnapayments.mp.utils.date_picker.utils.mediumRadius
import com.dnapayments.mp.utils.date_picker.utils.smallRadius
import com.dnapayments.mp.utils.date_picker.utils.xsmallPadding
import com.dnapayments.mp.utils.date_picker.utils.xxsmallPadding
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.ktor.util.date.WeekDay
import korlibs.time.Date
import korlibs.time.DateTime
import korlibs.time.DateTimeSpan

@Composable
internal fun DnaDatePicker(
    modifier: Modifier = Modifier,
    singleDatePicker: Boolean = false,
    minDate: Date? = null,
    maxDate: Date? = null,
    startDate: MutableState<Date?> = remember { mutableStateOf(null) },
    endDate: MutableState<Date?> = remember { mutableStateOf(null) },
    onDateSelected: (DateTime) -> Unit = { _ -> },
    onRangeDateSelected: (DateTime, DateTime) -> Unit = { _, _ -> },
    colors: MultiDatePickerColors = MultiDatePickerColors.defaultsDNA(),
    cardRadius: Dp = mediumRadius,
) {
    val localDensity = LocalDensity.current

    val weekDays = listOf(
        WeekDay.MONDAY,
        WeekDay.TUESDAY,
        WeekDay.WEDNESDAY,
        WeekDay.THURSDAY,
        WeekDay.FRIDAY,
        WeekDay.SATURDAY,
        WeekDay.SUNDAY
    )

    val allYears = (2015..2050).toList()

    val calendar = remember { mutableStateOf(DateTime.now()) }
    val datePickerState =
        remember { mutableStateOf(SHOWING_CALENDAR) }
    val yearScrollState = rememberLazyListState()
    val pickerHeight = remember { mutableStateOf(0.dp) }
    var offsetX by remember { mutableStateOf(0f) }
    var isSliding by remember { mutableStateOf(false) }
    var selectedTabIndex by remember {
        mutableStateOf(DatePickerAmPm.AM.index)
    }

    var timeInputState by remember {
        mutableStateOf(
            TimeInputState(
                hour = 0,
                minute = 0
            )
        )
    }

    LaunchedEffect(datePickerState.value) {
        if (datePickerState.value == SHOWING_YEAR_PICKER) {
            val yearIndex = allYears.indexOf(calendar.value.minus(DateTimeSpan(years = 3)).yearInt)
            yearScrollState.scrollToItem(yearIndex)
        }
    }

    LaunchedEffect(isSliding) {
        if (!isSliding) {
            if (offsetX > 1) {
                // Remove a month
                calendar.value = calendar.value.minus(DateTimeSpan(months = 1))
            } else if (offsetX < -1) {
                // Add a month
                calendar.value = calendar.value.plus(DateTimeSpan(months = 1))
            }
        }
    }

    Column(
        modifier
            .fillMaxWidth()
            .background(color = colors.cardColor, RoundedCornerShape(cardRadius))
    ) {
        /**
         * HEADER
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    Paddings.medium
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .noRippleClickable { datePickerState.value = SHOWING_YEAR_PICKER },
                verticalAlignment = Alignment.CenterVertically
            ) {
                DNAText(
                    text = calendar.value.toMonthYear(),
                    style = DnaTextStyle.Medium20.copy(color = colors.monthColor)
                )
                Spacer(
                    modifier = Modifier.width(Paddings.small)
                )
                Icon(
                    painter = painterResource(MR.images.chevron_forward),
                    tint = Color.Unspecified,
                    modifier = Modifier.width(7.dp).height(12.dp),
                    contentDescription = ""
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            MonthPickerIcon(Operation.MINUS, colors, calendar)

            Spacer(Modifier.width(32.dp))

            MonthPickerIcon(Operation.PLUS, colors, calendar)
        }

        AnimatedContent(
            targetState = datePickerState.value,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = ""
        ) { state ->
            when (state) {
                SHOWING_CALENDAR -> {
                    Column(
                        modifier = Modifier.onGloballyPositioned {
                            pickerHeight.value = with(localDensity) { it.size.height.toDp() }
                        },
                    ) {
                        /**
                         * DAYS
                         */
                        Row(modifier = Modifier.padding(horizontal = Paddings.small)) {
                            weekDays.map {
                                DNAText(
                                    text = it.value.uppercase(),
                                    style = DnaTextStyle.SemiBold13.copy(color = colors.weekDayColor),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f / 7f)
                                )
                            }
                        }
                        Spacer(Modifier.height(xxsmallPadding))

                        /**
                         * BODY
                         */
                        Column(
                            modifier = Modifier
                                .padding(horizontal = Paddings.small)
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { isSliding = true },
                                        onDragEnd = { isSliding = false },
                                    ) { change, dragAmount ->
                                        change.consume()
                                        offsetX = dragAmount.x
                                    }
                                },
                            verticalArrangement = Arrangement.spacedBy(Paddings.extraSmall),
                        ) {
                            val daysNumber: IntRange =
                                (1..calendar.value.month.days(calendar.value.yearInt))
                            val days: List<Date> = daysNumber.map {
                                calendar.value.copyDayOfMonth(
                                    dayOfMonth = it
                                ).date
                            }
                            val daysItem: MutableList<Date?> = days.toMutableList()
                            // ADD EMPTY ITEMS TO THE BEGINNING OF THE LIST IF FIRST WEEK DAY OF MONTH DON'T START ON THE FIRST DAY OF THE WEEK
                            daysItem.first()?.let {
                                val dayOfWeek = if (it.dayOfWeekInt == 0) 7 else it.dayOfWeekInt
                                (1 until dayOfWeek).forEach { _ ->
                                    daysItem.add(0, null)
                                }
                            }

                            val daysByWeek: List<MutableList<Date?>> =
                                daysItem.chunked(7) { it.toMutableList() }
                            // ADD EMPTY ITEMS TO THE END OF THE LIST IF LAST WEEK DAY OF MONTH DON'T START ON THE FIRST DAY OF THE WEEK
                            daysByWeek.last()
                                .let { list ->
                                    (1..7 - list.size).forEach { _ ->
                                        daysByWeek.last().add(
                                            null
                                        )
                                    }
                                }
                            val today = DateTime.now().date
                            daysByWeek.map {
                                Row {
                                    it.map { day ->
                                        val isSelected =
                                            day != null && (day == startDate.value || day == endDate.value)

                                        val isBetween = day != null
                                                && startDate.value != null
                                                && endDate.value != null
                                                && (day.isAfter(startDate.value!!) && day.isBefore(
                                            endDate.value!!
                                        ))
                                        val isEnabled = day != null
                                                && (minDate == null || day.isAfter(minDate) || day == minDate)
                                                && (maxDate == null || day.isBefore(maxDate) || day == maxDate)

                                        val selectedBackgroundColor = animateColorAsState(
                                            targetValue = if (isSelected) colors.selectedIndicatorColor else Color.Transparent,
                                            label = ""
                                        )
                                        val textColor = animateColorAsState(
                                            targetValue = if (isSelected) {
                                                colors.selectedDayNumberColor
                                            } else if (!isEnabled) {
                                                colors.disableDayColor
                                            } else {
                                                colors.dayNumberColor
                                            }, label = ""
                                        )

                                        Box(
                                            Modifier
                                                .weight(1f / 7f)
                                                .aspectRatio(1f)
                                                .background(
                                                    if (isBetween || isSelected && endDate.value != null) colors.selectedDayBackgroundColor else Color.Transparent,
                                                    if (isSelected) RoundedCornerShape(
                                                        topStartPercent = if (day == startDate.value) 100 else 0,
                                                        topEndPercent = if (day == endDate.value) 100 else 0,
                                                        bottomEndPercent = if (day == endDate.value) 100 else 0,
                                                        bottomStartPercent = if (day == startDate.value) 100 else 0,
                                                    ) else RoundedCornerShape(0)
                                                )
                                                .clip(CircleShape)
                                                .noRippleClickable {
                                                    if (!isEnabled || day == null)
                                                        return@noRippleClickable

                                                    when {
                                                        startDate.value == null -> {
                                                            startDate.value = day
                                                        }
                                                        endDate.value == null && !singleDatePicker -> {
                                                            if (day.isBefore(startDate.value!!)) startDate.value =
                                                                day
                                                            else if (day.isAfter(startDate.value!!)) endDate.value =
                                                                day
                                                            else if (day == startDate.value) startDate.value =
                                                                null
                                                        }
                                                        else -> {
                                                            startDate.value = day
                                                            endDate.value = null
                                                        }
                                                    }
                                                }
                                        ) {
                                            Box(
                                                Modifier
                                                    .fillMaxSize()
                                                    .border(
                                                        1.dp,
                                                        if (day != null && day.isToday(today))
                                                            greyColorAlpha else Color.Transparent,
                                                        CircleShape
                                                    )
                                                    .background(
                                                        selectedBackgroundColor.value,
                                                        CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                DNAText(
                                                    text = day?.day?.toString() ?: "",
                                                    style = DnaTextStyle.Normal20.copy(
                                                        color = textColor.value
                                                    ),
                                                    textAlign = TextAlign.Center,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /**
                         * FOOTER
                         */

                        if (singleDatePicker) {

                            Spacer(Modifier.height(Paddings.small))

                            Row(
                                modifier = Modifier.padding(
                                    start = Paddings.medium,
                                    end = Paddings.medium
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DNAText(
                                    text = stringResource(MR.strings.time),
                                    style = DnaTextStyle.Medium20
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                TimeInput(timeInputState) {
                                    datePickerState.value = SHOWING_TIME_PICKER
                                }
                                Spacer(modifier = Modifier.width(Paddings.small6dp))
                                DnaTimeSwitch(
                                    items = DatePickerAmPm.entries,
                                    selectedIndex = selectedTabIndex,
                                    onSelectionChange = {
                                        selectedTabIndex = it
                                    }
                                )
                            }
                        }

                        Spacer(Modifier.height(Paddings.small))

                        DNAYellowButton(
                            text = stringResource(MR.strings.apply),
                            screenName = ScreenName.DATE_PICKER,
                            onClick = {
                                val startDateValue = startDate.value!!

                                val hour = if (
                                    selectedTabIndex == DatePickerAmPm.PM.index && timeInputState.hour != 12) {
                                    timeInputState.hour + 12
                                } else {
                                    timeInputState.hour
                                }

                                val startDateTime = DateTime(
                                    startDateValue.year,
                                    startDateValue.month,
                                    startDateValue.day,
                                    hour,
                                    timeInputState.minute
                                )

                                when {
                                    singleDatePicker -> {
                                        onDateSelected(startDateTime)
                                    }
                                    else -> {
                                        val endDateValue = endDate.value!!
                                        onRangeDateSelected(
                                            startDateTime, DateTime(
                                                endDateValue.year,
                                                endDateValue.month,
                                                endDateValue.day
                                            ).dateDayEnd
                                        )
                                    }
                                }
                            },
                            enabled = if (singleDatePicker) {
                                startDate.value != null
                            } else {
                                startDate.value != null && endDate.value != null
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = Paddings.medium,
                                    end = Paddings.medium,
                                    bottom = Paddings.medium
                                )
                        )
                    }
                }
                SHOWING_TIME_PICKER -> {
                    WheelTimePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pickerHeight.value)
                    ) { hour, minute ->
                        timeInputState = timeInputState.copy(
                            hour = hour,
                            minute = minute
                        )
                        datePickerState.value = SHOWING_CALENDAR
                    }
                }
                SHOWING_YEAR_PICKER -> {
                    LazyColumn(
                        modifier = Modifier
                            .height(pickerHeight.value)
                            .fillMaxWidth(),
                        state = yearScrollState,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(allYears) { year ->
                            val isSelected = year == calendar.value.yearInt

                            Box(
                                modifier = Modifier
                                    .height(pickerHeight.value / 7)
                                    .clip(RoundedCornerShape(smallRadius))
                                    .noRippleClickable {
                                        calendar.value =
                                            calendar.value.set(year)
                                        datePickerState.value = SHOWING_CALENDAR
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                DNAText(
                                    year.toString(),
                                    style = if (isSelected) MaterialTheme.typography.headlineMedium.copy(
                                        color = colors.monthColor,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = poppinsFontFamily()
                                    )
                                    else MaterialTheme.typography.titleLarge.copy(
                                        color = colors.weekDayColor,
                                        fontWeight = FontWeight.Light,
                                        fontFamily = poppinsFontFamily()
                                    ),
                                    modifier = Modifier.padding(horizontal = xsmallPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class MultiDatePickerState {
    SHOWING_CALENDAR,
    SHOWING_TIME_PICKER,
    SHOWING_YEAR_PICKER
}

@Composable
fun MonthPickerIcon(
    operation: Operation,
    colors: MultiDatePickerColors,
    calendar: MutableState<DateTime>
) {
    return Icon(
        when (operation) {
            Operation.PLUS -> painterResource(MR.images.chevron_forward)
            Operation.MINUS -> painterResource(MR.images.chevron_backward)
        },
        contentDescription = null,
        tint = colors.iconColor,
        modifier = Modifier
            .noRippleClickable {
                val newDate = when (operation) {
                    Operation.PLUS -> calendar.value.plus(DateTimeSpan(months = 1))
                    Operation.MINUS -> calendar.value.minus(DateTimeSpan(months = 1))
                }
                calendar.value = newDate
            }
    )
}
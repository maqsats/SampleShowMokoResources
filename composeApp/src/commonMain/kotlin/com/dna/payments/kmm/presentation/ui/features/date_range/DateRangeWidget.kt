package com.dna.payments.kmm.presentation.ui.features.date_range

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.date_picker.DatePickerPeriod
import com.dna.payments.kmm.domain.model.date_picker.DateSelection
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.theme.greyFirst
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.date_picker.DnaDatePickerDialog
import com.dna.payments.kmm.utils.extension.ddMmYyyy
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import korlibs.time.DateTime

@Composable
fun DateRangeWidget(datePickerPeriod: DatePickerPeriod) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = Paddings.small),
            painter = painterResource(MR.images.ic_calendar),
            contentDescription = null
        )
        DNAText(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(datePickerPeriod.stringResource),
            style = DnaTextStyle.Medium14
        )
    }
}

@Composable
fun DateRangeBottomSheet(
    dateSelection: DateSelection,
    onDatePeriodClick: (DatePickerPeriod) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DnaDatePickerDialog(
            maxDate = DateTime.now().date,
            showDatePicker = showDatePicker,
            onDismiss = { showDatePicker = false },
            onRangeDateSelected = { startDate, endDate ->
                showDatePicker = false
                onDatePeriodClick(
                    DatePickerPeriod.CUSTOM(
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = white)
            .padding(Paddings.medium),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {

        DNAText(
            text = stringResource(MR.strings.date_range),
            style = DnaTextStyle.SemiBold20
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = Dimens.minimum,
                    color = greyColor,
                    shape = RoundedCornerShape(Dimens.small)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Paddings.small,
                        horizontal = Paddings.standard12dp
                    ).noRippleClickable {
                        showDatePicker = true
                    },
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DNAText(
                    text = dateSelection.startDate.ddMmYyyy(),
                    style = DnaTextStyle.Medium16
                )

                DNAText(
                    text = stringResource(MR.strings.hyphen),
                    style = DnaTextStyle.Medium16
                )

                DNAText(
                    text = dateSelection.endDate.ddMmYyyy(),
                    style = DnaTextStyle.Medium16
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(Paddings.small),
            horizontalArrangement = Arrangement.spacedBy(Paddings.small)
        ) {

            items(
                listOf(
                    DatePickerPeriod.TODAY(),
                    DatePickerPeriod.YESTERDAY(),
                    DatePickerPeriod.THIS_WEEK(),
                    DatePickerPeriod.LAST_WEEK(),
                    DatePickerPeriod.PREVIOUS_WEEK(),
                    DatePickerPeriod.CURRENT_MONTH(),
                    DatePickerPeriod.LAST_30_DAYS(),
                    DatePickerPeriod.LAST_MONTH(),
                    DatePickerPeriod.LAST_60_DAYS(),
                    DatePickerPeriod.LAST_90_DAYS(),
                    DatePickerPeriod.THIS_YEAR(),
                    DatePickerPeriod.LAST_12_MONTHS()
                )
            ) { item ->
                DatePeriodItem(
                    datePickerPeriod = item,
                    onClick = {
                        onDatePeriodClick(
                            item
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Paddings.minimum))
    }
}

@Composable
fun DatePeriodItem(datePickerPeriod: DatePickerPeriod, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.dateRangeHeight)
            .background(color = greyFirst)
            .noRippleClickable(onClick),
        contentAlignment = Alignment.Center
    ) {
        DNAText(
            text = stringResource(datePickerPeriod.stringResource),
            style = DnaTextStyle.Medium12Grey,
            textAlign = TextAlign.Center
        )
    }
}
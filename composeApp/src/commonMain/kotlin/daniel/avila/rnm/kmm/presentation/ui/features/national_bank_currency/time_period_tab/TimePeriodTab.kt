package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.time_period_tab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriodTab
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground

@Composable
fun TimePeriodsTab(
    modifier: Modifier,
    list: List<TimePeriodTab>,
    selectedTab: MutableState<TimePeriodTab>
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = 15.dp),
    ) {
        list.forEach { tabItem ->
            val isSelected = selectedTab.value == tabItem
            RoundedBackground(
                modifier = Modifier.wrapContentWidth(),
                backgroundColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
                height = 30.dp,
                paddingHorizontal = 14.dp,
                onClick = {
                    selectedTab.value = tabItem
                },
            ) {
                Text(
                    text = tabItem.stringResId,
                    style = MaterialTheme.typography.button,
                    color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
                )
            }

            if (tabItem != list.last()) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
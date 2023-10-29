package daniel.avila.rnm.kmm.domain.model.time_period_tab

data class TimePeriodTab(
    val stringResId: String,
    var isSelected: Boolean,
    var timePeriod: TimePeriod = TimePeriod.WEEK
)
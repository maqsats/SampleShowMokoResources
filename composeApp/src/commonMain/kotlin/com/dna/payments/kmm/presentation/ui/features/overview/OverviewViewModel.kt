package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.extension.getDefaultDateRange

class OverviewViewModel(val getDateRangeUseCase: GetDateRangeUseCase) :
    BaseViewModel<OverviewContract.Event, OverviewContract.State, OverviewContract.Effect>() {

    init {
        setState {
            copy(dateRange = getDateRangeUseCase(Menu.OVERVIEW))
        }
    }

    override fun createInitialState() = OverviewContract.State(
        dateRange = getDefaultDateRange()
    )

    override fun handleEvent(event: OverviewContract.Event) {
        when (event) {
            is OverviewContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    OverviewContract.Effect.OnPageChanged(event.position)
                }
            }
            is OverviewContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }
            }
        }
    }
}
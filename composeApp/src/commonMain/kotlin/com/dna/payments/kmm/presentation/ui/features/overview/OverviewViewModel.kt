package com.dna.payments.kmm.presentation.ui.features.overview

import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class OverviewViewModel :
    BaseViewModel<OverviewContract.Event, OverviewContract.State, OverviewContract.Effect>() {

    override fun createInitialState() = OverviewContract.State()

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
        }
    }
}
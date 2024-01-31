package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.product_guide

import com.dna.payments.kmm.domain.model.product_guide.ProductGuide
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface ProductGuideContract {

    sealed interface Event : UiEvent

    data class State(
        val productGuideList: List<ProductGuide>
    ) : UiState

    sealed interface Effect : UiEffect
}
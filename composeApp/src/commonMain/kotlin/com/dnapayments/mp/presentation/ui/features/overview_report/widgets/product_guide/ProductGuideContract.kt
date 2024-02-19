package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.product_guide

import com.dnapayments.mp.domain.model.product_guide.ProductGuide
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface ProductGuideContract {

    sealed interface Event : UiEvent

    data class State(
        val productGuideList: List<ProductGuide>
    ) : UiState

    sealed interface Effect : UiEffect
}
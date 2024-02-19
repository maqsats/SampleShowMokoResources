package com.dnapayments.mp.presentation.ui.features.overview_report.widgets.product_guide

import com.dnapayments.mp.domain.interactors.data_factory.product_guide.ProductGuideDataFactory
import com.dnapayments.mp.presentation.mvi.BaseViewModel

class ProductGuideViewModel(
    private val productGuideDataFactory: ProductGuideDataFactory
) : BaseViewModel<ProductGuideContract.Event, ProductGuideContract.State, ProductGuideContract.Effect>() {

    init {
        setState {
            copy(
                productGuideList = productGuideDataFactory(),
            )
        }
    }

    override fun createInitialState() = ProductGuideContract.State(
        productGuideList = emptyList()
    )

    override fun handleEvent(event: ProductGuideContract.Event) {

    }
}
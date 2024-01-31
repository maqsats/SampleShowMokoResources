package com.dna.payments.kmm.presentation.ui.features.overview_report.widgets.product_guide

import com.dna.payments.kmm.domain.interactors.data_factory.product_guide.ProductGuideDataFactory
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

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
package com.dna.payments.kmm.domain.interactors.data_factory.product_guide

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.product_guide.ProductGuide

class ProductGuideDataFactory {

    operator fun invoke(): List<ProductGuide> {
        return listOf(
            ProductGuide(
                imageBackground = MR.images.ic_guide_background2,
                title = MR.strings.product_guide,
                description = MR.strings.product_guide_description,
                detailLink = MR.strings.product_guide_detail_link
            ),
            ProductGuide(
                imageBackground = MR.images.ic_guide_background,
                title = MR.strings.product_guide_second,
                description = MR.strings.product_guide_second_description,
                detailLink = MR.strings.product_guide_second_detail_link
            )
        )
    }
}
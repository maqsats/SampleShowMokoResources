package com.dnapayments.mp.domain.interactors.data_factory.product_guide

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.product_guide.ProductGuide

class ProductGuideDataFactory {

    operator fun invoke(): List<ProductGuide> {
        return listOf(
            ProductGuide(
                imageBackground = "https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/ic_guide_background.svg?alt=media&token=99665d66-b348-4781-a524-a9839f2bbd5f",
                title = MR.strings.product_guide,
                description = MR.strings.product_guide_description,
                detailLink = MR.strings.product_guide_detail_link
            ),
            ProductGuide(
                imageBackground = "https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/ic_guide_background2.svg?alt=media&token=935bf1c0-3656-4805-8c60-17beb5ba917d",
                title = MR.strings.product_guide_second,
                description = MR.strings.product_guide_second_description,
                detailLink = MR.strings.product_guide_second_detail_link
            )
        )
    }
}
package com.dna.payments.kmm.domain.interactors.use_cases.payment_link

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.payment_links.PaymentLink
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkByPeriod
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkHeader
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.PaymentLinksRepository
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.extension.cutSubstringAfterDot
import com.dna.payments.kmm.utils.extension.isEqual
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.parse


class PaymentLinkUseCase(
    private val paymentLinksRepository: PaymentLinksRepository
) : Mapper<PaymentLink, List<PaymentLinkByPeriod>>() {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        status: String,
        page: Int,
        size: Int
    ): Response<List<PaymentLinkByPeriod>> = map(
        paymentLinksRepository.getPaymentLink(
            startDate = startDate,
            endDate = endDate,
            status = status,
            page = page,
            size = size
        )
    )

    override fun mapData(from: PaymentLink): List<PaymentLinkByPeriod> {
        val items = from.records

        val groupedItems = items.groupBy {
            val today = DateTime.now()
            val itemDate =
                DateFormat("yyyy-MM-dd HH:mm:ss").parse(it.createdDate.cutSubstringAfterDot())

            when {
                itemDate.isEqual(today) -> UiText.StringResource(MR.strings.today)
                itemDate.isEqual(today.minus(DateTimeSpan(days = 1))) -> UiText.StringResource(MR.strings.yesterday)
                else -> UiText.DynamicString(itemDate.format("dd MMMM yyyy"))
            }
        }

        val result = mutableListOf<PaymentLinkByPeriod>()

        groupedItems.entries.forEach { (headerTitle, itemList) ->
            // Add Header
            result.add(PaymentLinkHeader(title = headerTitle))

//            Add Items
            result.addAll(itemList)
        }
        return result
    }
}


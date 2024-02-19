package com.dnapayments.mp.domain.interactors.use_cases.payment_link

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.map.Mapper
import com.dnapayments.mp.domain.model.payment_links.PaymentLink
import com.dnapayments.mp.domain.model.payment_links.PaymentLinkByPeriod
import com.dnapayments.mp.domain.model.payment_links.PaymentLinkHeader
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.PaymentLinksRepository
import com.dnapayments.mp.utils.UiText
import com.dnapayments.mp.utils.extension.cutSubstringAfterDot
import com.dnapayments.mp.utils.extension.isEqual
import korlibs.time.DateFormat
import korlibs.time.DateTime
import korlibs.time.DateTimeSpan
import korlibs.time.parse


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

        val yesterdayItem = UiText.StringResource(MR.strings.yesterday)
        val todayItem = UiText.StringResource(MR.strings.today)

        val groupedItems = items.groupBy {
            val today = DateTime.now()
            val itemDate =
                DateFormat("yyyy-MM-dd HH:mm:ss").parse(it.createdDate.cutSubstringAfterDot())

            when {
                itemDate.isEqual(today) -> todayItem
                itemDate.isEqual(today.minus(DateTimeSpan(days = 1))) -> yesterdayItem
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


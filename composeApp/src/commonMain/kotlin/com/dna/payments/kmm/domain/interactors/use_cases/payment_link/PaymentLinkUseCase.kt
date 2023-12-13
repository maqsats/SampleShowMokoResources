package com.dna.payments.kmm.domain.interactors.use_cases.payment_link

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.payment_links.PaymentLink
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkByPeriod
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkHeader
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.PaymentLinksRepository
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.TimeFormat

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
        val sortedItems = items.sortedWith(compareByDescending { it.createdDate })

        val groupedItems = sortedItems.groupBy {
            val today = DateTime.now().toString()
            val itemDate = DateTime.parse(it.createdDate.substringBefore("T"))

            when {
                itemDate.isEqual(DateTime.parse(today)) -> "Today"
                itemDate.isEqual(DateTime.parse(today).minus(DateTimeSpan(days = 1))) -> "Yesterday"
                else -> itemDate.format("dd MMMM yyyy")
            }
        }

        val result = mutableListOf<PaymentLinkByPeriod>()

        groupedItems.entries.forEach { (headerTitle, itemList) ->
            // Add Header
            result.add(PaymentLinkHeader(title = headerTitle))

            // Add Items
            result.addAll(itemList)
        }

        return result
    }
}

private fun DateTimeTz.isEqual(other: DateTimeTz): Boolean {
    return this.year == other.year && this.month == other.month && this.dayOfMonth == other.dayOfMonth
}

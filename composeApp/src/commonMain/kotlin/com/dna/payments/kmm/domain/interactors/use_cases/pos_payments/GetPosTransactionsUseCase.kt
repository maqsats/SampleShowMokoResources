package com.dna.payments.kmm.domain.interactors.use_cases.pos_payments

import com.dna.payments.kmm.data.model.transactions.pos.PosRequestParam
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransaction
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransactions
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository

class GetPosTransactionsUseCase(
    private val posTransactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        posRequestParam: PosRequestParam
    ): Response<Pair<List<PosTransaction>, Int>> =
        when (val response = posTransactionRepository.getPosPaymentTransactions(
            posRequestParam = posRequestParam
        )) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.Success -> Response.Success(
                getPosTransaction(response.data)
            )

            Response.TokenExpire -> Response.NetworkError
        }

    private fun getPosTransaction(
        posTransactions: PosTransactions
    ): Pair<List<PosTransaction>, Int> {
        return Pair(posTransactions.data, posTransactions.totalCount)
    }
}
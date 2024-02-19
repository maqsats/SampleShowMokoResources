package com.dnapayments.mp.domain.interactors.use_cases.pos_payments

import com.dnapayments.mp.data.model.transactions.pos.PosRequestParam
import com.dnapayments.mp.domain.model.transactions.pos.PosTransaction
import com.dnapayments.mp.domain.model.transactions.pos.PosTransactions
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository

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
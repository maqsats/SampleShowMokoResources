package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.data.model.stores.Terminal
import com.dna.payments.kmm.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType

class GetDetailTerminalSettingsUseCase {
    operator fun invoke(
        stores: List<StoresItem>,
        paymentMethodType: PaymentMethodType,
        storeItemId: String
    ): List<DetailTerminalSetting> {
        val detailTerminalSettings = mutableListOf<DetailTerminalSetting>()
        stores.map { storeItem ->
            val allTerminals: List<Terminal>

            when (paymentMethodType) {
                PaymentMethodType.APPLE_PAY -> {
                    allTerminals = storeItem.terminals.filter { terminal ->
                        terminal.settings.applepay.isAvailable() && storeItem.id == storeItemId
                    }
                    detailTerminalSettings.addAll(allTerminals.map {
                        DetailTerminalSetting(
                            id = it.id,
                            status = it.settings.applepay.isActive(),
                            paymentTypeUrl = paymentMethodType
                        )
                    })
                }

                PaymentMethodType.GOOGLE_PAY -> {
                    allTerminals = storeItem.terminals.filter { terminal ->
                        terminal.settings.googlepay.isAvailable() && storeItem.id == storeItemId
                    }
                    detailTerminalSettings.addAll(allTerminals.map {
                        DetailTerminalSetting(
                            id = it.id,
                            status = it.settings.googlepay.isActive(),
                            paymentTypeUrl = paymentMethodType
                        )
                    })
                }

                PaymentMethodType.KLARNA -> {
                    allTerminals = storeItem.terminals.filter { terminal ->
                        terminal.settings.klarna.isAvailable() && storeItem.id == storeItemId
                    }
                    detailTerminalSettings.addAll(allTerminals.map {
                        DetailTerminalSetting(
                            id = it.id,
                            status = it.settings.klarna.isActive(),
                            paymentTypeUrl = paymentMethodType
                        )
                    })
                }

                PaymentMethodType.PAY_BY_BANK -> {
                    allTerminals = storeItem.terminals.filter { terminal ->
                        terminal.settings.payByBankApp.isAvailable() && storeItem.id == storeItemId
                    }
                    detailTerminalSettings.addAll(allTerminals.map {
                        DetailTerminalSetting(
                            id = it.id,
                            status = it.settings.payByBankApp.isActive(),
                            paymentTypeUrl = paymentMethodType
                        )
                    })
                }

                PaymentMethodType.PAY_PALL -> {

                }

                PaymentMethodType.OPEN_BANK -> {
                    allTerminals = storeItem.terminals.filter { terminal ->
                        terminal.settings.ecospend.isAvailable() && storeItem.id == storeItemId
                    }
                    detailTerminalSettings.addAll(allTerminals.map {
                        DetailTerminalSetting(
                            id = it.id,
                            status = it.settings.ecospend.isActive(),
                            paymentTypeUrl = paymentMethodType
                        )
                    })
                }
            }
        }
        return detailTerminalSettings
    }
}
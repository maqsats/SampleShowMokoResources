package com.dna.payments.kmm.domain.use_case

import com.dna.payments.kmm.data.model.stores.Setting
import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.APPLE_PAY
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.GOOGLE_PAY
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.KLARNA
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.OPEN_BANK
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.PAY_BY_BANK
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType.PAY_PALL
import com.dna.payments.kmm.domain.model.payment_methods.setting.SettingStatus
import com.dna.payments.kmm.domain.model.payment_methods.setting.TerminalSetting
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.StoresRepository

class GetTerminalSettingsUseCase(
    private val storesRepository: StoresRepository,
    private val getDetailTerminalSettingsUseCase: GetDetailTerminalSettingsUseCase
) {
    suspend operator fun invoke(paymentMethodType: PaymentMethodType): Response<List<TerminalSetting>> =
        when (val response = storesRepository.getStores()) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.TokenExpire -> Response.TokenExpire
            is Response.Success -> Response.Success(
                getTerminalList(
                    response.data,
                    paymentMethodType
                )
            )
        }

    private fun getTerminalList(
        storesApiModel: List<StoresItem>,
        paymentMethodType: PaymentMethodType
    ): List<TerminalSetting> = storesApiModel.map { storeItem ->

        var countTerminal = 0
        var activeTerminal = 0

        when (paymentMethodType) {
            APPLE_PAY -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.applepay.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.applepay.isActive()
                }
            }

            GOOGLE_PAY -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.googlepay.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.googlepay.isActive()
                }
            }

            KLARNA -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.klarna.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.klarna.isActive()
                }
            }

            PAY_BY_BANK -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.payByBankApp.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.payByBankApp.isActive()
                }
            }

            PAY_PALL -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.paypal.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.paypal.isActive()
                }
            }

            OPEN_BANK -> {
                countTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.ecospend.isAvailable()
                }
                activeTerminal = storeItem.terminals.count { terminal ->
                    terminal.settings.ecospend.isActive()
                }
            }
        }
        TerminalSetting(
            name = storeItem.name,
            paymentMethodType = paymentMethodType,
            countTerminal = countTerminal,
            activeTerminal = activeTerminal,
            id = storeItem.id,
            detailTerminalSettingList = getDetailTerminalSettingsUseCase(
                stores = storesApiModel,
                storeItemId = storeItem.id,
                paymentMethodType = paymentMethodType
            )
        )
    }
}

fun Setting?.isAvailable(): Boolean {
    if (this == null)
        return false
    return status != SettingStatus.UNAVAILABLE.status
}

fun Setting?.isActive(): Boolean {
    if (this == null)
        return false
    return status == SettingStatus.ACTIVE.status
}

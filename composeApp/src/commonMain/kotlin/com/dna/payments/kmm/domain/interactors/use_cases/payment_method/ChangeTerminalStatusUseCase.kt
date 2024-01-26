package com.dna.payments.kmm.domain.interactors.use_cases.payment_method

import com.dna.payments.kmm.data.model.payment_methods.NewTerminalStatusRequest
import com.dna.payments.kmm.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dna.payments.kmm.domain.model.payment_methods.setting.SettingStatus
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.SettingRepository

class ChangeTerminalStatusUseCase(val repository: SettingRepository) {
    suspend operator fun invoke(detailTerminalSetting: DetailTerminalSetting): Response<Unit> =
        repository.changingTerminalStatus(
            newTerminalStatusRequest = NewTerminalStatusRequest(
                terminalId = detailTerminalSetting.id,
                when (detailTerminalSetting.status) {
                    false -> SettingStatus.ACTIVE.status
                    true -> SettingStatus.DISABLED.status
                }
            ), detailTerminalSetting.paymentTypeUrl
        )
}
package com.dnapayments.mp.domain.interactors.use_cases.payment_method

import com.dnapayments.mp.data.model.payment_methods.NewTerminalStatusRequest
import com.dnapayments.mp.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dnapayments.mp.domain.model.payment_methods.setting.SettingStatus
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.SettingRepository

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
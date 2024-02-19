package com.dnapayments.mp.domain.interactors.use_cases.stores

import com.dnapayments.mp.domain.model.stores.CardSettings
import com.dnapayments.mp.data.model.stores.Terminal

interface StoresUseCase {

    fun findPrioritizedTerminal(terminals: List<Terminal>): Terminal

    fun getCardSettingsVR(terminal: Terminal): CardSettings
    fun getCardSettingsPL(terminal: Terminal): CardSettings
}
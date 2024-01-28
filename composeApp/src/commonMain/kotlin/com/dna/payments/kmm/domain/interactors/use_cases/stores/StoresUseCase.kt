package com.dna.payments.kmm.domain.interactors.use_cases.stores

import com.dna.payments.kmm.domain.model.stores.CardSettings
import com.dna.payments.kmm.data.model.stores.Terminal

interface StoresUseCase {

    fun findPrioritizedTerminal(terminals: List<Terminal>): Terminal

    fun getCardSettingsVR(terminal: Terminal): CardSettings
    fun getCardSettingsPL(terminal: Terminal): CardSettings
}
package com.dnapayments.mp.domain.interactors.use_cases.stores

import com.dnapayments.mp.data.model.stores.Terminal
import com.dnapayments.mp.domain.model.stores.CardSettings

class DefaultStoresUseCase : StoresUseCase {
    override fun findPrioritizedTerminal(terminals: List<Terminal>): Terminal {
        val activeDefaultTerminal = terminals.find { it.isActive && it.isDefault }

        if (activeDefaultTerminal != null) {
            return activeDefaultTerminal
        }

        val activeOrDefaultTerminal = terminals.find { it.isActive || it.isDefault }

        if (activeOrDefaultTerminal != null) {
            return activeOrDefaultTerminal
        }

        return terminals.first()
    }

    override fun getCardSettingsVR(terminal: Terminal): CardSettings {
        return CardSettings(
            allowRecurring = terminal.settings.card.allowMotoRecurring,
            recurringByDefault = terminal.settings.card.motoRecurringByDefault,
            transactionTypeSelection = terminal.settings.card.motoTransactionTypeSelection,
            allowNonCvv = terminal.settings.card.allowMotoNonCvv
        )
    }

    override fun getCardSettingsPL(terminal: Terminal): CardSettings {
        return CardSettings(
            allowRecurring = terminal.settings.card.allowMotoRecurring,
            recurringByDefault = terminal.settings.card.pblRecurringByDefault,
            transactionTypeSelection = terminal.settings.card.pblTransactionTypeSelection,
        )
    }
}
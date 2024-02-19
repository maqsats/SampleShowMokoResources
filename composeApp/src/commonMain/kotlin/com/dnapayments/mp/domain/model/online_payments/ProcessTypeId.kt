package com.dnapayments.mp.domain.model.online_payments

enum class ProcessTypeId(val value: Int) {
    InitialUCOF(1),
    UCOF_MIT(2),
    InitialCOF(3),
    COF_CIT(4),
    MailOrder(5),
    TelephoneOrder(6),
    InitialRecurring(7),
    RecurringMIT(8),
    MailOrderInitialCOF(9),
    TelephoneOrderInitialCOF(10),
    MOTORecurringMIT(11),
    OTHER(0);

    companion object {
        fun fromInt(value: Int? = 0): ProcessTypeId? {
            return entries.find { it.value == value }
        }
    }
}


package com.dna.payments.kmm.domain.model.pincode

sealed interface KeyboardItem {

    sealed interface Digit : KeyboardItem {
        val value: Int

        data object Zero : Digit {
            override val value: Int
                get() = 0
        }

        data object One : Digit {
            override val value: Int
                get() = 1
        }

        data object Two : Digit {
            override val value: Int
                get() = 2
        }

        data object Three : Digit {
            override val value: Int
                get() = 3
        }

        data object Four : Digit {
            override val value: Int
                get() = 4
        }

        data object Five : Digit {
            override val value: Int
                get() = 5
        }

        data object Six : Digit {
            override val value: Int
                get() = 6
        }

        data object Seven : Digit {
            override val value: Int
                get() = 7
        }

        data object Eight : Digit {
            override val value: Int
                get() = 8
        }

        data object Nine : Digit {
            override val value: Int
                get() = 9
        }
    }

    data object Biometric : KeyboardItem

    data object Erase : KeyboardItem
}

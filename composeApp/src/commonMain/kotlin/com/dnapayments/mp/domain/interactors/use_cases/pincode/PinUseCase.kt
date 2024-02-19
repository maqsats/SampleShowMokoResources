package com.dnapayments.mp.domain.interactors.use_cases.pincode

import com.dnapayments.mp.data.preferences.Preferences

class PinUseCase(
    private val preferences: Preferences
) {

    fun isPinCodeExist(): Boolean {
        return preferences.getPinCode().isNotBlank()
    }

    fun savePin(pinString: String) {
//        cipherManager.put(
//            pinString
//        )
        preferences.setPinCode(pinString)
    }

    fun isPinCorrect(pinString: String): Boolean {
        return loadPin() == pinString
    }

    fun loadPin(): String {
//        return cipherManager.get()
        return preferences.getPinCode()
    }


    fun resetPin() {
//        cipherManager.delete()
        preferences.setPinCode("")
    }

    fun clearAccessToken() {
        preferences.clear()
    }

    fun logout() {
        preferences.clear()
    }
}
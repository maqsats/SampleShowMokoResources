package com.dna.payments.kmm.utils.constants

object Constants {
    const val CLIENT_ID = "mp_android"
    const val CLIENT_SECRET = "69AxknXmVu7SWT2gSfLUtsmxJ3r29PbU5H9R"
    const val GRANT_TYPE = "password"
    const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    const val IS_DEBUG = true
    const val SCOPE =
        "webapi usermanagement email_send verification statement statistics payment virtual_terminal"
    const val BASE_AUTH_URL = "https://test-oauth.dnapayments.com/"
    const val BASE_RESTORE_URL = "https://test-portal.dnapayments.com/oppapi/client/"
    const val BASE_URL = "https://test-api.dnapayments.com/"
    const val URL_HELP = "https://dnapayments.com/faq/"
    const val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"

    const val initialTime = 25
    const val delayInMillis = 1000L
}
package com.dnapayments.mp.utils.constants

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
    const val CREDENTIALS_HEADER = "Authorization"
    const val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"

    const val initialTime = 25
    const val delayInMillis = 1000L

    const val UCOF = "ucof"

    const val GBP = "GBP"
    const val SCREEN_OPEN_EVENT = "screen_open"
    const val BUTTON_CLICK_EVENT = "button_click"
    const val NAV_ITEM = "nav_item"
    const val SCREEN_NAME = "screen_name"
    const val BUTTON_NAME = "button_name"

    const val HELP_CENTER_URL =
        "https://chatwidget.kodif.io/?app_id=09f51c83-c25e-4288-901b-d35d9e7e0cd8&app_position=right&demo_page=true&is_mobile=false&playground=true"

    const val ROLE = "role"
    const val ACTIVE = "active"
    const val PAGE = "page"
    const val SIZE = "size"
}
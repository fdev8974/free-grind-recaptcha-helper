package dev.estopia.free_grind.recaptcha

object MintContract {
    const val EXTRA_ACTION = "dev.estopia.free_grind.recaptcha.extra.ACTION"
    const val EXTRA_TOKEN = "dev.estopia.free_grind.recaptcha.extra.TOKEN"
    const val EXTRA_ERROR = "dev.estopia.free_grind.recaptcha.extra.ERROR"
    const val EXTRA_ERROR_DETAIL = "dev.estopia.free_grind.recaptcha.extra.ERROR_DETAIL"

    const val TRUSTED_CALLER_PACKAGE = "dev.estopia.free_grind"
    const val GRINDR_PACKAGE = "com.grindrapp.android"

    val GRINDR_ACTIONS = setOf(
        "sign_up",
        "login",
        "forgot_password",
        "report",
        "decision_appeal",
        "device_key_registration",
    )
}

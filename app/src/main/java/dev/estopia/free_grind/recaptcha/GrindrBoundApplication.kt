package dev.estopia.free_grind.recaptcha

import android.app.Application

class GrindrBoundApplication : Application() {
    override fun getPackageName(): String = MintContract.GRINDR_PACKAGE
}

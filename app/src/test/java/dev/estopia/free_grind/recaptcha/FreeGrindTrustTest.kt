package dev.estopia.free_grind.recaptcha

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FreeGrindTrustTest {
    private val release = "7CE9E5CDAC620EC798BEA6749AED4C6D59BF12AB5E1DC8CB59C117BB929FC9D0"
    private val local = "7EAEC68446F1C2E14D2749807CD63629319C4E59015A16A408A6BD668B7FE48E"

    private fun signedBy(vararg certs: String) =
        FreeGrindTrust.SigningCertificates { _, sha256 ->
            certs.any { it.hexToByteArray().contentEquals(sha256) }
        }

    @Test
    fun `trusts the release signer`() {
        assertTrue(FreeGrindTrust.trusts("dev.estopia.free_grind", signedBy(release)))
    }

    @Test
    fun `trusts the local dev signer`() {
        assertTrue(FreeGrindTrust.trusts("dev.estopia.free_grind", signedBy(local)))
    }

    @Test
    fun `refuses another signer on the right package`() {
        val impostor = "0".repeat(64)
        assertFalse(FreeGrindTrust.trusts("dev.estopia.free_grind", signedBy(impostor)))
    }

    @Test
    fun `refuses another package even when it holds a trusted certificate`() {
        assertFalse(FreeGrindTrust.trusts("com.grindrapp.android", signedBy(release)))
    }

    @Test
    fun `refuses a caller android could not name`() {
        assertFalse(FreeGrindTrust.trusts(null, signedBy(release)))
    }

    @Test
    fun `asks only about the trusted package, never the caller's own name`() {
        val asked = mutableListOf<String>()
        FreeGrindTrust.trusts("dev.estopia.free_grind") { name, _ ->
            asked += name
            false
        }
        assertTrue(asked.all { it == FreeGrindTrust.PACKAGE })
    }
}

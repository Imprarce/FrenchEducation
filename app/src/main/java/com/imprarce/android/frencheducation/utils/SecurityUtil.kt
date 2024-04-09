package com.imprarce.android.frencheducation.utils

import java.security.MessageDigest

class SecurityUtil(password: String) {
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString(separator = "") { "%02x".format(it) }
    }
}
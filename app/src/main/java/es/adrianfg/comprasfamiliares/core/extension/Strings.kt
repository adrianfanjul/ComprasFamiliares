package es.adrianfg.comprasfamiliares.core.extension

import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val regex =
        Regex("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:\\w(?:[\\w-]*\\w)?\\.)+(\\w(?:[\\w-]*\\w))$")
    return matches(regex)
}

/**
 * Extension method to check if String is valid pass.
 */
fun String.isValidPass(): Boolean {
    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
    return matches(regex)
}

/**
 * Extension method to check if String is valid Name.
 */
fun String.isValidName(): Boolean {
    val regex = Regex("[a-zA-ZñÑáéíóúÁÉÍÓÚ 0-9_-]{3,40}")
    return matches(regex)
}

/**
 * Extension method to check if String is valid Description.
 */
fun String.isValidDescription(): Boolean {
    val regex = Regex("[a-zA-ZñÑáéíóúÁÉÍÓÚ 0-9_-]{3,80}")
    return matches(regex)
}

/**
 * Extension method to code a string
 */
fun get_SHA_512_SecurePassword(passwordToHash: String, salt: String): String {

    var generatedPassword=""

    try {
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        md.update(salt.toByteArray())
        val bytes: ByteArray = md.digest(passwordToHash.toByteArray())
        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(Integer.toString((bytes[i] and 0xff) + 0x100, 16).substring(1))
        }
        generatedPassword = sb.toString()
    } catch (e: NoSuchAlgorithmException) {
        throw Error(e.message)
    }
    return generatedPassword
}
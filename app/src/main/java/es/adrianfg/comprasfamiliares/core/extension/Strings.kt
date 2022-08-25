package es.adrianfg.comprasfamiliares.core.extension

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val regex = Regex("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:\\w(?:[\\w-]*\\w)?\\.)+(\\w(?:[\\w-]*\\w))$")
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
    val regex = Regex("[a-zA-Z ]{3,50}")
    return matches(regex)
}
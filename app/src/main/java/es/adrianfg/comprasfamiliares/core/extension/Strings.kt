package es.adrianfg.comprasfamiliares.core.extension

/*
 *Estension metod to check if string is Email
 */
/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val regex = Regex("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:\\w(?:[\\w-]*\\w)?\\.)+(\\w(?:[\\w-]*\\w))$")
    return matches(regex)
}

fun String.isValidPass(): Boolean {
    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
    return matches(regex)
}
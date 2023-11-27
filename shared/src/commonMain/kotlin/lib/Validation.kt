package lib


fun isEmailInvalid(email: String): Boolean {
    if(email.isEmpty() || email.contains(" ") || !email.contains("@") || !email.contains(".")) {
        return true
    }
    return false
}

fun isNameInvalid(name: String): Boolean {
if(!("^[A-Za-z]+$".toRegex().matches(name)))
        return true
    return false
}

fun isPhoneNumberInvalid(phoneNumber: String): Boolean {
    if(phoneNumber.isEmpty() || phoneNumber.contains(" ") || phoneNumber.length != 11 || (phoneNumber.toDoubleOrNull() === null))
        return true
    return false
}

fun isPasswordInvalid(password: String): Boolean {
    if(password.isEmpty() || password.contains(" ")) return true
    return false
}

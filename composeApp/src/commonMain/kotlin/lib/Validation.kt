package lib


fun isEmailInvalid(email: String): Boolean {
    return !("[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
        .toRegex().matches(email))
}

fun isNameInvalid(name: String): Boolean {
    return !("^[A-Za-z]+$".toRegex().matches(name))
}

fun isPhoneNumberInvalid(phoneNumber: String): Boolean {
    return !("[(]?\\d{3}[)]?\\s?-?\\s?\\d{3}\\s?-?\\s?\\d{4}".toRegex()).matches(phoneNumber)
}

fun isPasswordInvalid(password: String): Boolean {
    return password.isEmpty() || password.contains(" ")
}

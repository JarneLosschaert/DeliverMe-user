package be.howest.jarnelosschaert.deliverme.logic.data

enum class AddressScreenStatus(val subtitle: String) {
    RECEIVER_ADDRESS("Receiver address details"),
    SENDER_ADDRESS("Sender address details"),
    CUSTOMER_EDIT_ADDRESS("Edit address details"),
}
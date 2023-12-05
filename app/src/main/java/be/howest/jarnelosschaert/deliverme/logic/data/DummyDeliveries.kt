package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import be.howest.jarnelosschaert.deliverme.logic.models.DeliveryState
import be.howest.jarnelosschaert.deliverme.logic.models.Package
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize

val dummyDeliveries: List<Delivery> = dummyContacts.take(10).mapIndexed { index, contact ->
    Delivery(
        id = index + 1,
        dateTimeArrived = "13:00",
        dateTimeDeparted = "14:00",
        state = DeliveryState.values()[index % DeliveryState.values().size],
        packageInfo = Package(
            id = index + 1,
            sender = contact,
            receiver = dummyContacts[(index + 1) % 10],
            senderAddress = contact.homeAddress,
            receiverAddress = dummyContacts[(index) % 10].homeAddress,
            packageSize = PackageSize.values()[index % PackageSize.values().size],
            description = "Package $index",
            fee = 5.0 * (index + 1)
        )
    )
}

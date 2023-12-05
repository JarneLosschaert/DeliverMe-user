package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.Package
import be.howest.jarnelosschaert.deliverme.logic.models.PackageSize

val dummyPackages: List<Package> = dummyContacts.take(10).mapIndexed { index, contact ->
    Package(
        id = index + 1,
        sender = contact,
        receiver = dummyContacts[(index + 1) % 10], // Just an example, you may adjust as needed
        senderAddress = contact.homeAddress,
        receiverAddress = dummyContacts[(index) % 10].homeAddress, // Just an example, you may adjust as needed
        packageSize = PackageSize.values()[index % PackageSize.values().size],
        description = "Package $index",
        fee = 5.0 * (index + 1)
    )
}

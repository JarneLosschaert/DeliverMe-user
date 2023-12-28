package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.*
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = Address(-1, "", "", "", "", 0.0, 0.0)
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson, listOf())
val defaultDriver = Driver(-1, "", defaultPerson)
val defaultPackage = Package(-1, defaultCustomer, defaultCustomer, defaultAddress, defaultAddress, PackageSize.small, "", 0.0)
val defaultDelivery = Delivery(-1, "", "", DeliveryState.failed, defaultPackage, defaultDriver)
val defaultPayResponse = PayResponse("", "")

val defaultContact = Customer(-1, defaultAddress, defaultPerson, listOf())

package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.*
import be.howest.jarnelosschaert.deliverme.logic.services.responses.PayResponse
import java.time.DateTimeException
import java.time.LocalDateTime

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = Address(-1, "", "", "", "")
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson, listOf())
val defaultPackage = Package(-1, defaultCustomer, defaultCustomer, defaultAddress, defaultAddress, PackageSize.SMALL, "", 0.0)
val defaultDelivery = Delivery(-1, LocalDateTime.now(), LocalDateTime.now(), DeliveryState.FAILED, defaultPackage)
val defaultPayResponse = PayResponse("", "")

val defaultContactPerson = Person(-1, "No contacts yet", "", "")
val defaultContactAddress = Address(-1, "Street", "Nr", "Zip", "City")
val defaultContact = Customer(-1, defaultContactAddress, defaultContactPerson, listOf())

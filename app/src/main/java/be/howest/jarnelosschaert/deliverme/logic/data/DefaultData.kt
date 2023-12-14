package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.*

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = Address(-1, "", "", "", "")
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson, listOf())
val defaultPackage = Package(-1, defaultCustomer, defaultCustomer, defaultAddress, defaultAddress, PackageSize.SMALL, "", 0.0)
val defaultDelivery = Delivery(-1, "", "", DeliveryState.FAILED, defaultPackage)
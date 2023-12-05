package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.deliverme.logic.models.Person

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = Address(-1, "", "", "", "")
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson)


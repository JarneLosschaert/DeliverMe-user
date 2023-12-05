package be.howest.jarnelosschaert.deliverme.logic.data

import be.howest.jarnelosschaert.deliverme.logic.models.Customer
import be.howest.jarnelosschaert.deliverme.logic.models.HomeAddress
import be.howest.jarnelosschaert.deliverme.logic.models.Person

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = HomeAddress(-1, "", "", "", "")
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson)


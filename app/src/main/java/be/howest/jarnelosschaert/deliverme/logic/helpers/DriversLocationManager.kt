package be.howest.jarnelosschaert.deliverme.logic.helpers

import android.content.Context
import be.howest.jarnelosschaert.deliverme.logic.helpers.notifications.Location
import be.howest.jarnelosschaert.deliverme.logic.models.Delivery
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DriversLocationManager(private val context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "DriversLocationPrefs"
        private const val KEY_DRIVERS_LOCATION = "driversLocation"
    }

    fun saveDriversLocation(driversLocation: Map<Int, Location>) {
        val jsonString = Json.encodeToString(DriversLocationWrapper(driversLocation))
        preferences.edit().putString(KEY_DRIVERS_LOCATION, jsonString).apply()
    }

    fun getDriversLocation(): Map<Int, Location> {
        val jsonString = preferences.getString(KEY_DRIVERS_LOCATION, null)
        return if (jsonString != null) {
            val wrapper = Json.decodeFromString<DriversLocationWrapper>(jsonString)
            wrapper.driversLocation
        } else {
            emptyMap()
        }
    }

    fun getDriverLocation(driverId: Int): Location? {
        return getDriversLocation()[driverId]
    }

    fun addDriverLocation(driverId: Int, location: Location) {
        val currentDriversLocation = getDriversLocation().toMutableMap()
        currentDriversLocation[driverId] = location
        saveDriversLocation(currentDriversLocation)
    }

    private fun removeDriverLocation(driverId: Int) {
        val currentDriversLocation = getDriversLocation().toMutableMap()
        currentDriversLocation.remove(driverId)
        saveDriversLocation(currentDriversLocation)
    }

    fun removeDriverLocations(deliveries: List<Delivery>) {
        for (delivery in deliveries) {
            removeDriverLocation(delivery.id)
        }
    }

    @Serializable
    private data class DriversLocationWrapper(val driversLocation: Map<Int, Location>)
}

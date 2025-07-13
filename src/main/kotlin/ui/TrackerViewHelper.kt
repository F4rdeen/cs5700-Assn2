package ui

import core.Shipment
import core.ShipmentObserver
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import simulator.TrackingSimulator
import java.text.SimpleDateFormat
import java.util.*

class TrackerViewHelper : ShipmentObserver {
    private var shipment: Shipment? = null

    // State properties from original UML
    val shipmentStatus = SimpleStringProperty("")
    val shipmentNotes: ObservableList<String> = FXCollections.observableArrayList()
    val shipmentUpdateHistory: ObservableList<String> = FXCollections.observableArrayList()
    val expectedShipmentDeliveryDate = SimpleStringProperty("")
    val currentLocation = SimpleStringProperty("")

    fun trackShipment(id: String) {
        // Stop tracking current shipment if any
        shipment?.removeObserver(this)

        // Find new shipment
        shipment = TrackingSimulator.findShipment(id)
        if (shipment == null) {
            shipmentStatus.set("core.Shipment not found")
            return
        }

        shipment?.addObserver(this)
        updateDisplay()
    }

    fun stopTracking() {
        shipment?.removeObserver(this)
        shipment = null
        clearDisplay()
    }

    override fun onUpdate(shipment: Shipment) {
        updateDisplay()
    }

    private fun updateDisplay() {
        shipment?.let {
            shipmentStatus.set(it.status)
            currentLocation.set(it.currentLocation)
            expectedShipmentDeliveryDate.set(formatDate(it.expectedDeliveryDateTimestamp))
            shipmentNotes.setAll(it.notes)
            shipmentUpdateHistory.setAll(it.updateHistory.map { update ->
                "core.Shipment went from ${update.previousStatus} to ${update.newStatus} on ${formatDate(update.timestamp)}"
            })
        }
    }

    private fun clearDisplay() {
        shipmentStatus.set("")
        currentLocation.set("")
        expectedShipmentDeliveryDate.set("")
        shipmentNotes.clear()
        shipmentUpdateHistory.clear()
    }

    private fun formatDate(timestamp: Long): String {
        if (timestamp <= 0) return "Unknown"
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(date)
    }
}
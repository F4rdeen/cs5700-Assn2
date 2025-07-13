import tornadofx.App
import ui.MainView

class ShipmentTrackerApp : App(MainView::class)

fun main() {
    ShipmentTrackerApp().launch()
}
package pattern.behavioral

import java.util.*

fun main() {
    val weatherData = WeatherData()

    val currentDisplay1: Observer = CurrentConditionsDisplay(weatherData)

    weatherData.setMeasurements(29f, 65f, 745)
    weatherData.setMeasurements(39f, 70f, 760)
    weatherData.setMeasurements(42f, 72f, 763)
    
}

interface Observer {
    fun update(temperature: Float, humidity: Float, pressure: Int)
}

interface Observable {
    fun registerObserver(o: Observer)
    fun removeObserver(o: Observer)
    fun notifyObservers()
}

class WeatherData : Observable {
    private val observers: MutableList<Observer> = LinkedList()
    
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        for (observer in observers) observer.update(temperature, humidity, pressure)
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Int) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        notifyObservers()
    }
}

class CurrentConditionsDisplay(weatherData: Observable) : Observer {
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0

    init {
        weatherData.registerObserver(this)
    }

    override fun update(temperature: Float, humidity: Float, pressure: Int) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        display()
    }

    fun display() {
        System.out.printf(
            "Сейчас значения:%.1f градусов цельсия и %.1f %% влажности. Давление %d мм рт. ст.\n",
            temperature,
            humidity,
            pressure
        )
    }
}
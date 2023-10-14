package uz.itschool.weatherapp.model

data class ByHour(
    var img: String,
    var time: String,
    var degree: String,
    val isDay: Int
)

package uz.itschool.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.itschool.weatherapp.ui.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
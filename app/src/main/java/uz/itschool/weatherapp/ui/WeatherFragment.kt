package uz.itschool.weatherapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import uz.itschool.weatherapp.R
import uz.itschool.weatherapp.adapter.HourlyAdapter
import uz.itschool.weatherapp.adapter.PostAdapter
import uz.itschool.weatherapp.databinding.FragmentWeatherBinding
import uz.itschool.weatherapp.model.ByHour
import uz.itschool.weatherapp.model.Post

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WeatherFragment : Fragment() {
    var url: String =
        "https://api.weatherapi.com/v1/forecast.json?key=e0ff523620584e678d873504230810&q=Uzbekistan&days=6&aqi=yes&alerts=no"

    lateinit var postList: MutableList<Post>
    lateinit var hourlyList: MutableList<ByHour>
    lateinit var requestque: RequestQueue
    lateinit var binding: FragmentWeatherBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        requestque = Volley.newRequestQueue(requireContext())
        postList = mutableListOf()
        hourlyList = mutableListOf()



        loadData()



        return binding.root
    }

    private fun loadHourly(dateCurrent: String) {
        val request = JsonObjectRequest(url,
            { response ->
                hourlyList = mutableListOf()
                postList= mutableListOf()
                val forecast = response.getJSONObject("forecast")
                val forecastday = forecast.getJSONArray("forecastday")

                for (i in 0 until forecastday.length()) {
                    val obj = forecastday.getJSONObject(i)
                    val date = obj.getString("date")
                    val day = obj.getJSONObject("day")

                    val condition = day.getJSONObject("condition")
                    val textCondition = condition.getString("text")
                    val degree = day.getString("avgtemp_c")

                    val post = Post(textCondition, degree, date)
                    postList.add(post)
                    val hour = obj.getJSONArray("hour")
                    for (i in 0 until hour.length()) {
                        val obj2 = hour.getJSONObject(i)
                        val time = obj2.getString("time")
                        val conditionHourly = obj2.getJSONObject("condition")
                        val textConditionHourly = conditionHourly.getString("text")
                        val degreeHourly = obj2.getString("temp_c")

                        val isDay = obj2.getInt("is_day")

                        val arr = time.split(" ".toRegex(), limit = 2).toTypedArray()
                        val dateHourly = arr[0]
                        Log.d("ARR", "onCreateView: $dateHourly")



                        if (dateHourly == dateCurrent) {
                            hourlyList.add(
                                ByHour(
                                    textConditionHourly,
                                    time,
                                    degreeHourly,
                                    isDay
                                )
                            )
                            Log.d("TAG", "onCreateView: ${hourlyList.size}")
                        }

                        binding.timeDegree.adapter = HourlyAdapter(hourlyList)
                        binding.timeDegree.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    }

                }
            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestque.add(request)
    }

    private fun loadData() {
        var main: String
        var day: Int
        var temp: Double
        var country: String
        var city: String
        var currentDate: String = ""
        val request = JsonObjectRequest(url,
            { response ->
                val a = response!!.getJSONObject("current")
                currentDate = a.getString("last_updated")

                val arrCurrent = currentDate.split(" ".toRegex(), limit = 2).toTypedArray()
                val dateCurrent = arrCurrent[0]
                val timeCurrent = arrCurrent[1]

                val timeArrCurrent = timeCurrent.split(":".toRegex()).toTypedArray()
                val hourNumCurrent = timeArrCurrent[0].toInt()

                val condition = a.getJSONObject("condition")
                main = condition.getString("text")
                binding.main.text = main
                temp = a.getDouble("temp_c")
                binding.temp.text = (temp).toString()
                val location = response.getJSONObject("location")
                country = location.getString("country")
                binding.country.text = country
                city = location.getString("region")
                binding.city.text = city

                day = a.getInt("is_day")
                if (day == 1) {
                    if (main == "Clear") {
                        binding.condition.setImageResource(R.drawable.sunny)
                    }
                    if (main == "Sunny") {
                        binding.condition.setImageResource(R.drawable.sunny)
                    }
                    if (main == "Overcast") {
                        binding.condition.setImageResource(R.drawable.thunder)
                    } else {
                        binding.condition.setImageResource(R.drawable.sunny)
                    }
                    binding.back.setBackgroundResource(R.drawable.clear_sky)
                    binding.textView.setTextColor(Color.parseColor("#1D2837"))
                    binding.textView2.setTextColor(Color.parseColor("#1D2837"))
                    binding.temp.setTextColor(Color.parseColor("#1D2837"))
                    binding.main.setTextColor(Color.parseColor("#1D2837"))
                    binding.country.setTextColor(Color.parseColor("#1D2837"))
                    binding.city.setTextColor(Color.parseColor("#1D2837"))
                    binding.degree.setTextColor(Color.parseColor("#1D2837"))
                } else {
                    if (main == "Clear") {
                        binding.condition.setImageResource(R.drawable.moon)
                    }
                    if (main == "Overcast") {
                        binding.condition.setImageResource(R.drawable.thunder)
                    } else {
                        binding.condition.setImageResource(R.drawable.moon)
                    }
                    binding.back.setBackgroundResource(R.drawable.clear_sky_night)
                    binding.textView.setTextColor(Color.WHITE)
                    binding.textView2.setTextColor(Color.WHITE)
                    binding.temp.setTextColor(Color.WHITE)
                    binding.main.setTextColor(Color.WHITE)
                    binding.country.setTextColor(Color.WHITE)
                    binding.city.setTextColor(Color.WHITE)
                    binding.degree.setTextColor(Color.WHITE)
                }

                val forecast = response.getJSONObject("forecast")
                val forecastday = forecast.getJSONArray("forecastday")

                for (i in 0 until forecastday.length()) {
                    val obj = forecastday.getJSONObject(i)
                    val date = obj.getString("date")

                    val day = obj.getJSONObject("day")

                    val condition = day.getJSONObject("condition")
                    val textCondition = condition.getString("text")
                    val degree = day.getString("avgtemp_c")

                    val post = Post(textCondition, degree, date)
                    postList.add(post)
                    binding.recyclerView.adapter =
                        PostAdapter(postList, object : PostAdapter.MyPost {
                            override fun onItemClick(post: Post) {
                                loadHourly(post.day)
                                Log.d("TAG", "onItemClick: ")
                            }

                        })
                    binding.recyclerView.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    val hour = obj.getJSONArray("hour")
                    for (i in 0 until hour.length()) {
                        val obj2 = hour.getJSONObject(i)
                        val time = obj2.getString("time")
                        val conditionHourly = obj2.getJSONObject("condition")
                        val textConditionHourly = conditionHourly.getString("text")
                        val degreeHourly = obj2.getString("temp_c")

                        val isDay = obj2.getInt("is_day")

                        val arr = time.split(" ".toRegex(), limit = 2).toTypedArray()
                        val dateHourly = arr[0]
                        val timeHourly = arr[1]
                        Log.d("ARR", "onCreateView: $dateHourly")

                        val timeArr = timeHourly.split(":".toRegex()).toTypedArray()
                        val hourNum = timeArr[0].toInt()


                        if (dateHourly == dateCurrent) {
                            if (hourNum >= hourNumCurrent) {
                                hourlyList.add(
                                    ByHour(
                                        textConditionHourly,
                                        time,
                                        degreeHourly,
                                        isDay
                                    )
                                )
                                Log.d("ARR", "onCreateView: ${hourlyList.size}")
                            }
                        }

                        binding.timeDegree.adapter = HourlyAdapter(hourlyList)
                        binding.timeDegree.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    }

                }
            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestque.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(uz.itschool.weatherapp.ui.ARG_PARAM1, param1)
                    putString(uz.itschool.weatherapp.ui.ARG_PARAM2, param2)
                }
            }
    }
}
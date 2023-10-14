package uz.itschool.weatherapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.weatherapp.R
import uz.itschool.weatherapp.model.ByHour
import uz.itschool.weatherapp.model.Post

class HourlyAdapter(var postList: MutableList<ByHour>): RecyclerView.Adapter<HourlyAdapter.CustomAdapter>() {

    class CustomAdapter(item:View):RecyclerView.ViewHolder(item){
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val degree: TextView = itemView.findViewById(R.id.item_gradus)
        val time: TextView = itemView.findViewById(R.id.item_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_hourly, parent, false)

        return CustomAdapter(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {
        val itemsViewModel = postList[position]
        if (itemsViewModel.isDay == 0){
            if (itemsViewModel.img == "Sunny"){
                holder.imageView.setImageResource(R.drawable.moon)
            }
            if (itemsViewModel.img == "Clear"){
                holder.imageView.setImageResource(R.drawable.moon)
            }
            if (itemsViewModel.img == "Overcast") {
                holder.imageView.setImageResource(R.drawable.thunder)
            }
        }else {
            if (itemsViewModel.img == "Sunny") {
                holder.imageView.setImageResource(R.drawable.sunny)
            }
            if (itemsViewModel.img == "Clear") {
                holder.imageView.setImageResource(R.drawable.sunny)
            }
            if (itemsViewModel.img == "Overcast") {
                holder.imageView.setImageResource(R.drawable.thunder)
            }
        }
        holder.degree.text = "${itemsViewModel.degree}°"
        holder.time.text = itemsViewModel.time
    }
}
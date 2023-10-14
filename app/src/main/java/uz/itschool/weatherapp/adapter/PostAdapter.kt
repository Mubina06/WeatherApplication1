package uz.itschool.weatherapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.weatherapp.R
import uz.itschool.weatherapp.model.Post

class PostAdapter(var postList: MutableList<Post>, var mypost: MyPost) :
    RecyclerView.Adapter<PostAdapter.CustomAdapter>() {

    class CustomAdapter(item: View) : RecyclerView.ViewHolder(item) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val degree: TextView = itemView.findViewById(R.id.item_gradus)
        val day: TextView = itemView.findViewById(R.id.item_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)

        return CustomAdapter(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {
        val itemsViewModel = postList[position]
        if (itemsViewModel.image == "Sunny") {
            holder.imageView.setImageResource(R.drawable.sunny)
        }
        if (itemsViewModel.image == "Clear") {
            holder.imageView.setImageResource(R.drawable.sunny)
        }
        if (itemsViewModel.image == "Overcast") {
            holder.imageView.setImageResource(R.drawable.thunder)
        }
        holder.degree.text = "${itemsViewModel.degree}°"
        holder.day.text = itemsViewModel.day

        holder.imageView.setOnClickListener {
            mypost.onItemClick(itemsViewModel)
            notifyDataSetChanged()
        }
    }

    interface MyPost {
        fun onItemClick(post: Post)
    }
}
package com.ryleeg.vucourseold.ui.course


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.data.model.Course

class CourseAdapter(private val data: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(property: Course) {
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val description = view.findViewById<TextView>(R.id.tvDescription)

            title.text = property.name
            description.text = property.description

            // Glide.with(view.context).load(property.image).centerCrop().into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }


}
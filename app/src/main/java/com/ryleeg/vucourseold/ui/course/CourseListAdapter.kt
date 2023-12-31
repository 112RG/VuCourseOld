package com.ryleeg.vucourseold.ui.course


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.data.model.Course
import java.util.Locale

class CourseListAdapter(private var dataList: List<Course>) :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>(), Filterable {
    var dataFiltered: List<Course> = ArrayList()

    class CourseListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(property: Course) {
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val description = view.findViewById<TextView>(R.id.tvDescription)

            title.text = property.name
            description.text = property.description
        }
    }

    fun addData(list: List<Course>) {
        dataList = list as ArrayList<Course>
        dataFiltered = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CourseListViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFiltered.size
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        holder.bind(dataFiltered[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                dataFiltered = if (charString.isEmpty()) dataList else {
                    val filteredList = ArrayList<Course>()
                    dataList
                        .filter {
                            (it.description.lowercase(Locale.getDefault()).contains(charString)) or
                                    (it.name.lowercase(Locale.getDefault()).contains(charString))
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                Log.e("performFiltering: t1: ", dataFiltered.size.toString())

                return FilterResults().apply { values = dataFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Course>
                notifyDataSetChanged()

                Log.e("performFiltering: t2 ", "called" + dataFiltered.size)
            }
        }
    }
}
package com.ryleeg.vucourseold.ui.schedule


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.data.model.ScheduleItem

class ScheduleAdapter(private var dataList: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleListViewHolder>() {
    var dataFiltered: List<ScheduleItem> = ArrayList()

    class ScheduleListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(property: ScheduleItem) {
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val description = view.findViewById<TextView>(R.id.tvDescription)

            title.text = property.name
            description.text = property.description
        }
    }

    fun addData(list: List<ScheduleItem>) {
        dataList = list as ArrayList<ScheduleItem>
        dataFiltered = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ScheduleListViewHolder(v)
    }


    override fun getItemCount(): Int {
        return dataFiltered.size
    }

    override fun onBindViewHolder(holder: ScheduleListViewHolder, position: Int) {
        holder.bind(dataFiltered[position])
    }

}
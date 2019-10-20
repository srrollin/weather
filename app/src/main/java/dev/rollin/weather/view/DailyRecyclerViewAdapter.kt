package dev.rollin.weather.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.rollin.weather.R
import dev.rollin.weather.model.Data
import kotlinx.android.synthetic.main.fragment_daily.view.*
import java.text.SimpleDateFormat
import java.util.*

class DailyRecyclerViewAdapter : RecyclerView.Adapter<DailyRecyclerViewAdapter.ViewHolder>()
{
    private var mValues: MutableList<Data> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_daily, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        item.time?.run { holder.mIdView.text = getDateTime(toLong()) }

        holder.mContentView.text = item.summary
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
    }

    fun setItems(items: List<Data>) {
        mValues.clear()
        mValues.addAll(items)
        notifyDataSetChanged()
    }

    private fun getDateTime(timestamp: Long): String? {
        val pattern = SimpleDateFormat("dd")
        val date = Date(timestamp * 1000)
        return pattern.format(date)
    }
}

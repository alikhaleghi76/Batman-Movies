package ali.khaleghi.batman.view.adapter

import ali.khaleghi.batman.R
import ali.khaleghi.batman.service.model.detail.RatingsItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RatesAdapter(private val rateList: List<RatingsItem?>?) :
    RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rating, parent, false)
        return RatesViewHolder(itemView)
    }

    inner class RatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var value: TextView = view.findViewById(R.id.value)
        var source: TextView = view.findViewById(R.id.source)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        if (rateList == null || rateList.isEmpty()) return
        val (value, source) = rateList[position]!!

        holder.value.text = value
        holder.source.text = source

    }

    override fun getItemCount(): Int {
        if (rateList == null || rateList.isEmpty()) return 0
        return rateList.size
    }
}
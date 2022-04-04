package it.coffee.smartcoffee.presentation.size

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.coffee.smartcoffee.R

class SizesAdapter (private val items: List<SizeListItem>, private val listener: (style: SizeListItem) -> Unit) : RecyclerView.Adapter<SizesAdapter.SizeViewHolder>() {

    class SizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SizeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.textView.text = items[position].name
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0)
        holder.textView.setOnClickListener {
            listener(items[position])
        }
    }

    override fun getItemCount() = items.size

}
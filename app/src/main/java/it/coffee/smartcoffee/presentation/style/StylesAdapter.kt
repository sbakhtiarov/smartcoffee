package it.coffee.smartcoffee.presentation.style

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.coffee.smartcoffee.R

class StylesAdapter(
    private val items: List<StyleListItem>,
    private val listener: (style: StyleListItem) -> Unit,
) : RecyclerView.Adapter<StylesAdapter.StyleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StyleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return StyleViewHolder(view)
    }

    override fun onBindViewHolder(holder: StyleViewHolder, position: Int) {
        holder.textView.text = items[position].name
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0)
        holder.textView.setOnClickListener {
            listener(items[position])
        }
    }

    override fun getItemCount() = items.size

    class StyleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView as TextView
    }

}
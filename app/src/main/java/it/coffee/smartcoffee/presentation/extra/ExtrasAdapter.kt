package it.coffee.smartcoffee.presentation.extra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.coffee.smartcoffee.R

class ExtrasAdapter(private val items: List<ExtraListItem>) :
    RecyclerView.Adapter<ExtrasAdapter.ExtraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.extra_list_item, parent, false)
        return ExtraViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtraViewHolder, position: Int) {
        (holder.itemView as ExtraListItemView).bind(items[position])
    }

    override fun getItemCount() = items.size

    class ExtraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
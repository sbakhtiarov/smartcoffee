package it.coffee.smartcoffee.presentation.extra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.coffee.smartcoffee.R

class ExtraListAdapter(private val callback: (extraId: String, choiceId: String) -> Unit) : ListAdapter<ExtraListItem, ExtraListAdapter.ExtraViewHolder>(ExtraDiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.extra_list_item, parent, false)
        return ExtraViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtraViewHolder, position: Int) {
        (holder.itemView as ExtraListItemView).bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ExtraViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        with((holder.itemView as ExtraListItemView)) {
            bind(getItem(position), payloads)
            callback = this@ExtraListAdapter.callback
        }
    }

    class ExtraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

object ExtraDiffCallback : DiffUtil.ItemCallback<ExtraListItem>() {
    override fun areItemsTheSame(oldItem: ExtraListItem, newItem: ExtraListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExtraListItem, newItem: ExtraListItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ExtraListItem, newItem: ExtraListItem): Any? {
        oldItem.choices.zip(newItem.choices).forEach { (old, new) ->
            if (new.selected && !old.selected) {
                return new.id
            }
        }
        return null
    }


}
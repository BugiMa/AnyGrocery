package com.example.anygrocery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.databinding.ItemListCollectionBinding
import com.example.anygrocery.data.local.model.ShoppingList

class ListCollectionAdapter(
    private val itemClickCallback: ((ShoppingList) -> Unit)?,
    private val itemLongClickCallback: ((ShoppingList) -> Unit)?,
    private val archiveButtonClickCallback: ((ShoppingList) -> Unit)?
): RecyclerView.Adapter<ListCollectionAdapter.ListViewHolder>() {

    private var listCollection = emptyList<ShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemListCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = listCollection[position]
        val groceriesDone = "${list.checkedCount}/${list.allCount}"

        holder.bind(list.name, groceriesDone, list.isArchived!!)
    }

    inner class ListViewHolder(private val itemBinding: ItemListCollectionBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            val root = itemBinding.root
            val archiveButton = itemBinding.archiveImageButton
            root.setOnClickListener {
                itemClickCallback?.invoke(listCollection[absoluteAdapterPosition])
            }
            root.setOnLongClickListener {
                itemLongClickCallback?.invoke(listCollection[absoluteAdapterPosition])
                return@setOnLongClickListener true
            }
            archiveButton.setOnClickListener {
                archiveButtonClickCallback?.invoke(listCollection[absoluteAdapterPosition])
            }
        }
        fun bind(name: String, groceriesDone: String, isArchived: Boolean) {
            if (isArchived)itemBinding.archiveImageButton.visibility = View.GONE
            itemBinding.listName.text = name
            itemBinding.groceriesDone.text = groceriesDone
            itemBinding.archiveImageButton.visibility = if (listCollection[absoluteAdapterPosition].isArchived!!) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return listCollection.size
    }

    fun setData(newData: List<ShoppingList>) {
        this.listCollection = newData
        notifyDataSetChanged()
    }
}

package com.example.anygrocery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.databinding.ItemListCollectionBinding
import com.example.anygrocery.model.ShoppingList

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
        val current = listCollection[position]
        val groceriesDone = "${current.allCount}/${current.checkedCount}"
        holder.bind(current.listName, groceriesDone)
    }

    inner class ListViewHolder(private val itemBinding: ItemListCollectionBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            val root = itemBinding.root
            val archiveButton = itemBinding.archiveImageButton
            root.setOnClickListener {
                itemClickCallback?.invoke(listCollection[adapterPosition])
            }
            root.setOnLongClickListener {
                itemLongClickCallback?.invoke(listCollection[adapterPosition])
                return@setOnLongClickListener true
            }
            archiveButton.setOnClickListener {
                archiveButtonClickCallback?.invoke(listCollection[adapterPosition])
            }
        }
        fun bind(name: String, groceriesDone: String) {
            itemBinding.listName.text = name
            itemBinding.groceriesDoneNumber.text = groceriesDone
            itemBinding.archiveImageButton.visibility = if (listCollection[adapterPosition].isArchived!!) View.GONE else View.VISIBLE
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

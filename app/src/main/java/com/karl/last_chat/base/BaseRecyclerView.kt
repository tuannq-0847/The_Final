package com.karl.last_chat.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class BaseRecyclerView<Item>(
    callBack: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseRecyclerView.BaseViewHolder<Item>>(
    AsyncDifferConfig.Builder<Item>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun submitList(list: MutableList<Item>?) {
        super.submitList(list ?: mutableListOf())
    }

    abstract fun getLayoutRes(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(getLayoutRes(viewType), parent, false)
        )
    }

    open class BaseViewHolder<Item>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun onBind(item: Item) {}
    }
}

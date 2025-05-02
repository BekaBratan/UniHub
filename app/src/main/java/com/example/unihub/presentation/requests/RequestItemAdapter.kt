package com.example.unihub.presentation.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.databinding.ItemRequestBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class RequestItemAdapter: RecyclerView.Adapter<RequestItemAdapter.RequestsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<String>) {
        differ.submitList(list)
    }

    var listenerClickItem: RcViewItemClickIdCallback? = null
    fun setOnItemClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickItem = listener
    }

    inner class RequestsViewHolder(
        var binding: ItemRequestBinding
    ) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun onBind(item: String) {
            binding.run {
                tvRequestsName.text = item

                root.setOnClickListener {
                    listenerClickItem?.onClick(item.hashCode())
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestsViewHolder {
        return RequestsViewHolder(
            ItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
package com.example.unihub.presentation.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.admin.UsersListResponseItem
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.databinding.ItemRequestBinding
import com.example.unihub.databinding.ItemUserAdminBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import com.example.unihub.utils.RcViewItemClickIdStringCallback

open class ClubsListItemAdapter: RecyclerView.Adapter<ClubsListItemAdapter.RequestsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ClubsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClubsResponseItem,
            newItem: ClubsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ClubsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickUserName: RcViewItemClickIdCallback? = null
    fun setOnUserNameClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickUserName = listener
    }

    var listenerClickDelete: RcViewItemClickIdCallback? = null
    fun setOnDeleteClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickDelete = listener
    }

    inner class RequestsViewHolder(
        var binding: ItemUserAdminBinding
    ) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun onBind(item: ClubsResponseItem) {
            binding.run {
                tvUserName.text = item.head.name + " " + item.head.surname

                tvClubName.text = item.name

                tvUserName.setOnClickListener {
                    listenerClickUserName?.onClick(item.head.id)
                }

                ibDelete.setOnClickListener {
                    listenerClickDelete?.onClick(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestsViewHolder {
        return RequestsViewHolder(
            ItemUserAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}
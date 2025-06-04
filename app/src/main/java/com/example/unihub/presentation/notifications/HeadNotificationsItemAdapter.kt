package com.example.unihub.presentation.notifications

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.data.model.club_request.MyCreateClubResponse
import com.example.unihub.data.model.club_request.MyCreateClubResponseItem
import com.example.unihub.data.model.ticket.MyTicketsResponse
import com.example.unihub.data.model.ticket.MyTicketsResponseItem
import com.example.unihub.data.model.ticket.PendingTicketsResponseItem
import com.example.unihub.databinding.ItemRequestBinding
import com.example.unihub.utils.RcViewItemClickIdCallback
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
open class HeadNotificationsItemAdapter: RecyclerView.Adapter<HeadNotificationsItemAdapter.RequestsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PendingTicketsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: PendingTicketsResponseItem,
            newItem: PendingTicketsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PendingTicketsResponseItem,
            newItem: PendingTicketsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PendingTicketsResponseItem>) {
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
        fun onBind(item: PendingTicketsResponseItem) {
            binding.run {
                tvRequestsName.text = item.poster?.eventTitle
                tvName.text = item.poster?.eventDate

                tvTime.text = getTimeAgo(item.createdAt.toString())

                root.setOnClickListener {
                    listenerClickItem?.onClick(item.id)
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

    fun getTimeAgo(isoTime: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val updatedTime = ZonedDateTime.parse(isoTime, formatter)
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val duration = Duration.between(updatedTime, now)

        return when {
            duration.toMinutes() < 1 -> "just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
            duration.toHours() < 24 -> "${duration.toHours()} hours ago"
            duration.toDays() < 7 -> "${duration.toDays()} days ago"
            else -> updatedTime.toLocalDate().toString() // Or format if needed
        }
    }
}
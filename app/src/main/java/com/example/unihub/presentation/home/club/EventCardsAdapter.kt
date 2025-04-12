package com.example.unihub.presentation.home.club

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unihub.R
import com.example.unihub.data.model.EventsResponseItem
import com.example.unihub.databinding.ItemEventCardBinding
import com.example.unihub.utils.RcViewItemClickIdStringCallback

open class EventCardsAdapter: RecyclerView.Adapter<EventCardsAdapter.EventsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<EventsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: EventsResponseItem,
            newItem: EventsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EventsResponseItem,
            newItem: EventsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<EventsResponseItem>) {
        differ.submitList(list)
    }

    var listenerClickCard: RcViewItemClickIdStringCallback? = null
    fun setOnCardClickListener(listener: RcViewItemClickIdStringCallback) {
        this.listenerClickCard = listener
    }

    inner class EventsViewHolder(
        var binding: ItemEventCardBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(event: EventsResponseItem) {
            binding.run {
                tvEventName.text = event.name
                tvDescription.text = event.description
                tvDate.text = event.date

//                tvSeats.text = event.location

//                Glide.with(itemView.context)
//                    .load(event.image)
//                    .into(ivPostImage)

                root.setOnClickListener {
                    listenerClickCard?.onClick(event.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        return EventsViewHolder(
            ItemEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: EventsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
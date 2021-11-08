package com.space.test.ui.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.space.test.databinding.ItemRoomBinding

class RoomListAdapter(private val onRoomClicked: (RoomItem) -> Unit) :
    RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>() {
    private val items = arrayListOf<RoomItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRoomClicked
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(data: List<RoomItem>) {
        with(items) {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class RoomViewHolder(
        private val binding: ItemRoomBinding,
        private val onRoomClicked: (RoomItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoomItem) {
            binding.room = item
            binding.executePendingBindings()
            binding.root.setOnClickListener { onRoomClicked.invoke(item) }
        }
    }

}
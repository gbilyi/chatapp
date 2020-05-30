package com.gordbilyi.chatapp.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.databinding.ChatdetailMyMessageItemBinding
import com.gordbilyi.chatapp.databinding.ChatdetailTheirMessageItemBinding
import java.text.SimpleDateFormat
import java.util.*


/**
 * Adapter for the chat detail list.
 */
class ChatDetailAdapter(private val viewModel: ChatDetailViewModel) :
        ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val TYPE_MY_MESSAGE = 0
        private const val TYPE_THEIR_MESSAGE = 1
    }

    private lateinit var rv: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MY_MESSAGE -> MyMessageViewHolder.create(parent)
            TYPE_THEIR_MESSAGE -> TheirMessageViewHolder.create(parent)
            else -> throw IllegalArgumentException("$viewType is not supported!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        var prevItem: Message? = null
        if (position > 0) {
            prevItem = getItem(position - 1)
        }

        val showDate: Boolean
        showDate = if (prevItem != null) {
            val sdf = SimpleDateFormat("yyyyMMdd", Locale.CANADA)
            sdf.format(item.createdAt) != sdf.format(prevItem.createdAt)
        } else {
            true
        }

        when (getItemViewType(position)) {
            TYPE_MY_MESSAGE -> (holder as MyMessageViewHolder).bind(viewModel, item, showDate)
            TYPE_THEIR_MESSAGE -> (holder as TheirMessageViewHolder).bind(viewModel, item, showDate)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val messageItem = getItem(position)
        return if (messageItem.isLocal) {
            TYPE_MY_MESSAGE
        } else {
            TYPE_THEIR_MESSAGE
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCurrentListChanged(previousList: MutableList<Message>, currentList: MutableList<Message>) {
        super.onCurrentListChanged(previousList, currentList)
        rv.layoutManager?.itemCount?.let {
            rv.layoutManager?.scrollToPosition(it - 1)
        }
    }

    class MyMessageViewHolder private constructor(private val binding: ChatdetailMyMessageItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ChatDetailViewModel, item: Message, showDate: Boolean) {
            binding.showDate = showDate
            binding.viewmodel = viewModel
            binding.message = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): MyMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatdetailMyMessageItemBinding.inflate(layoutInflater, parent, false)
                return MyMessageViewHolder(binding)
            }

        }
    }

    class TheirMessageViewHolder private constructor(private val binding: ChatdetailTheirMessageItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ChatDetailViewModel, item: Message, showDate: Boolean) {
            binding.showDate = showDate
            binding.viewmodel = viewModel
            binding.message = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): TheirMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatdetailTheirMessageItemBinding.inflate(layoutInflater, parent, false)
                return TheirMessageViewHolder(binding)
            }

        }
    }


}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}

package com.vaibhavdhunde.app.friendlychat.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

class MessagesListAdapter : ListAdapter<FriendlyMessage, MessageViewHolder>(MESSAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = getItem(position)
        currentMessage?.let {
            holder.bind(currentMessage)
        }
    }

    companion object {
        private val MESSAGE_COMPARATOR = object : DiffUtil.ItemCallback<FriendlyMessage>() {
            override fun areItemsTheSame(
                oldItem: FriendlyMessage,
                newItem: FriendlyMessage
            ): Boolean {
                return oldItem.text == newItem.text
            }

            override fun areContentsTheSame(
                oldItem: FriendlyMessage,
                newItem: FriendlyMessage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
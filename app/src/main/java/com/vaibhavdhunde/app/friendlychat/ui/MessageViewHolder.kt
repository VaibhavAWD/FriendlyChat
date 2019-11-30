package com.vaibhavdhunde.app.friendlychat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavdhunde.app.friendlychat.R
import com.vaibhavdhunde.app.friendlychat.databinding.ItemTextMsgBinding
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

class MessageViewHolder(
    private val itemTextMsgBinding: ItemTextMsgBinding
) : RecyclerView.ViewHolder(itemTextMsgBinding.root) {

    fun bind(message: FriendlyMessage) {
        itemTextMsgBinding.message = message
        itemTextMsgBinding.executePendingBindings()
    }

    companion object {
        operator fun invoke(parent: ViewGroup): MessageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_msg, parent, false)
            val binding = ItemTextMsgBinding.bind(view)
            return MessageViewHolder(binding)
        }
    }
}
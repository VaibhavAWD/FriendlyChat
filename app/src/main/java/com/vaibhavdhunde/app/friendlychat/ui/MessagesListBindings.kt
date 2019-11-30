package com.vaibhavdhunde.app.friendlychat.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

object MessagesListBindings {

    @JvmStatic
    @BindingAdapter("app:messages")
    fun setMessages(rv: RecyclerView, messages: List<FriendlyMessage>?) {
        with(rv.adapter as MessagesListAdapter) {
            messages?.let {
                submitList(it)
                rv.scrollToPosition(this.itemCount - 1)
            }
        }
    }
}
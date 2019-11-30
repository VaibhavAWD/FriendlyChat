package com.vaibhavdhunde.app.friendlychat.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.vaibhavdhunde.app.friendlychat.firebase.FireDb
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

class MainViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<FriendlyMessage>?>()
    val messages: LiveData<List<FriendlyMessage>?> = _messages

    // Two-wau DataBinding
    var text = MutableLiveData<String>()

    private lateinit var messagesListener: ChildEventListener

    override fun onCleared() {
        super.onCleared()
        FireDb.removeMessagesEventListener(messagesListener)
    }

    fun fetchMessages() {
        messagesListener = FireDb.addMessagesEventListener { _messages.value = it }
    }

    fun sendMessage() {
        if (!isValidMessage()) return

        val msg = FriendlyMessage(text.value!!)
        FireDb.sendMessage(msg)

        // erase the text message after the message is sent
        resetMessage()
    }

    private fun isValidMessage(): Boolean {
        var isValid = false

        val textMsg = text.value

        if (!textMsg.isNullOrEmpty()) { // message should not be empty
            isValid = true
        }

        return isValid
    }

    private fun resetMessage() {
        text.value = ""
    }
}
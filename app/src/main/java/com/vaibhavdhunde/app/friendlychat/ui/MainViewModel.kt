package com.vaibhavdhunde.app.friendlychat.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vaibhavdhunde.app.friendlychat.firebase.FireDb
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

class MainViewModel : ViewModel() {

    // Two-wau DataBinding
    var text = MutableLiveData<String>()

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
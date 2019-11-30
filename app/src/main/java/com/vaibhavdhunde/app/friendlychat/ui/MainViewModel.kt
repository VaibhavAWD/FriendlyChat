package com.vaibhavdhunde.app.friendlychat.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.database.ChildEventListener
import com.vaibhavdhunde.app.friendlychat.R
import com.vaibhavdhunde.app.friendlychat.firebase.FireAuth
import com.vaibhavdhunde.app.friendlychat.firebase.FireDb
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage
import com.vaibhavdhunde.app.friendlychat.util.Event

class MainViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<FriendlyMessage>?>()
    val messages: LiveData<List<FriendlyMessage>?> = _messages

    // Two-wau DataBinding
    var text = MutableLiveData<String>()

    private val _authenticateUser = MutableLiveData<Event<Unit>>()
    val authenticateUser: LiveData<Event<Unit>> = _authenticateUser

    private val _showMessage = MutableLiveData<Event<Int>>()
    val showMessage: LiveData<Event<Int>> = _showMessage

    private var messagesListener: ChildEventListener? = null

    private var username: String? = null

    override fun onCleared() {
        super.onCleared()
        removeMessagesEventListener()
    }

    fun fetchMessages() {
        messagesListener = FireDb.addMessagesEventListener { _messages.value = it }
    }

    fun sendMessage() {
        if (!isValidMessage()) return

        val msg = FriendlyMessage(text.value!!, username)
        FireDb.sendMessage(msg)

        // erase the text message after the message is sent
        resetMessage()
    }

    fun initUser() {
        username = FireAuth.getCurrentUser()?.displayName
    }

    fun handleErrors(response: IdpResponse) {
        when (response.error!!.errorCode) {
            ErrorCodes.NO_NETWORK -> showMessage(R.string.error_no_network)
            ErrorCodes.UNKNOWN_ERROR -> showMessage(R.string.error_unknown)
        }
    }

    fun logoutUser() {
        FireAuth.logoutUser()
        removeMessagesEventListener()
        _authenticateUser.value = Event(Unit)
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

    fun checkUserAuthentication() {
        if (!FireAuth.isUserLoggedIn()) {
            _authenticateUser.value = Event(Unit)
        } else {
            fetchMessages()
        }
    }

    private fun showMessage(message: Int) {
        _showMessage.postValue(Event(message))
    }

    private fun removeMessagesEventListener() {
        messagesListener?.let { FireDb.removeMessagesEventListener(it) }
    }
}

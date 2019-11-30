package com.vaibhavdhunde.app.friendlychat.firebase

import com.google.firebase.database.*
import com.vaibhavdhunde.app.friendlychat.firebase.FireDb.Collections.MESSAGES
import com.vaibhavdhunde.app.friendlychat.model.FriendlyMessage

// Helper class that interprets friendly messages with app and firebase realtime database
object FireDb {

    // list of collection nodes names
    private object Collections {
        const val MESSAGES = "messages"
    }

    private val db: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    // Reference to `messages` node in firebase realtime database
    private val mMsgRef: DatabaseReference = db.reference.child(MESSAGES)

    // sends message to database using `push()` method
    // which generates unique ID for each message stored in the database
    fun sendMessage(message: FriendlyMessage) {
        mMsgRef.push().setValue(message)
    }

    fun addMessagesEventListener(onListen: (List<FriendlyMessage>?) -> Unit): ChildEventListener {
        val listener = object : ChildEventListener {
            val messages = mutableListOf<FriendlyMessage>()

            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                val message = data.getValue(FriendlyMessage::class.java)
                message?.let { messages.add(message) }
                onListen(messages.toList())
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        }
        mMsgRef.addChildEventListener(listener)
        return listener
    }

    fun removeMessagesEventListener(listener: ChildEventListener) {
        mMsgRef.removeEventListener(listener)
    }
}
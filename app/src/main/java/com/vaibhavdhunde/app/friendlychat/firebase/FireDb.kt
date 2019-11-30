package com.vaibhavdhunde.app.friendlychat.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
}
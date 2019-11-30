package com.vaibhavdhunde.app.friendlychat.model

data class FriendlyMessage(
    var text: String,
    var name: String? = "Anonymous",
    var photoUrl: String? = null
) {
    constructor() : this("")
}

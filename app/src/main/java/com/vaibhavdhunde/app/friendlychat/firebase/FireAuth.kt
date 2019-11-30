package com.vaibhavdhunde.app.friendlychat.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FireAuth {

    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun getCurrentUser(): FirebaseUser? {
        return if (isUserLoggedIn()) mAuth.currentUser else null
    }

    fun isUserLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    fun logoutUser() {
        mAuth.signOut()
    }
}
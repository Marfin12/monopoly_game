package com.example.experiments2

import android.app.Application
import android.content.Intent
import android.content.res.Resources
import com.example.experiments2.network.local.GamePreference
import com.example.experiments2.network.remote.fetch.FirebaseRemote
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MyApplication : Application() {
    companion object {
        lateinit var appResources: Resources
        lateinit var googleIntent: Intent
        lateinit var firebaseRemote: FirebaseRemote
        lateinit var gamePreference: GamePreference
    }

    override fun onCreate() {
        super.onCreate()
        appResources = resources

        firebaseRemote = FirebaseRemote.getInstance()
        gamePreference = GamePreference.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleIntent = mGoogleSignInClient.signInIntent
    }
}
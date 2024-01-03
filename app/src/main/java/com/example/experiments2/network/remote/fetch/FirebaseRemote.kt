package com.example.experiments2.network.remote.fetch

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import com.example.experiments2.constant.Constant
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.FirebaseUtil.validatePath
import com.example.experiments2.network.remote.response.user.ProfileData
import com.example.experiments2.util.Util.shuffle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.math.BigInteger
import java.security.MessageDigest


class FirebaseRemote {

    private val timeoutDuration = 10000L
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var timeoutRunnable: Runnable

    fun getFirebaseData(fieldName: MutableList<String>, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()
        handleRTO(fetchRemote)

        var reference = FirebaseDatabase.getInstance().reference

        validatePath(fieldName).forEach { path -> reference = reference.child(path) }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fetchRemote.onDataRetrieved?.invoke(snapshot)
                fetchRemote.onComplete?.invoke()

                handler.removeCallbacks(timeoutRunnable)
                reference.removeEventListener(this)
            }

            override fun onCancelled(p0: DatabaseError) {
                fetchRemote.onCancelled?.invoke(p0.message)
                fetchRemote.onComplete?.invoke()

                handler.removeCallbacks(timeoutRunnable)
                reference.removeEventListener(this)
            }
        })
    }

    fun insertFirebaseData(
        data: HashMap<String, Any>,
        fieldName: MutableList<String>,
        fetchRemote: FetchRemote
    ) {
        fetchRemote.onLoading?.invoke()
        handleRTO(fetchRemote)

        val completionListener = DatabaseReference.CompletionListener { error, _ ->
            handler.removeCallbacks(timeoutRunnable)

            if (error == null) fetchRemote.onDataRetrieved?.invoke(null)
            else fetchRemote.onCancelled?.invoke(error.message)

            fetchRemote.onComplete?.invoke()
        }

        var reference = FirebaseDatabase.getInstance().reference

        validatePath(fieldName).forEach { field -> reference = reference.child(field) }
        reference.updateChildren(data, completionListener)
    }

    fun guestData(context: Context) : ProfileData = ProfileData(
        getGuestUniqueName(context),
        getTokenFromGuest(getGuestUniqueName(context))
    )

    fun getTokenFromEmail(
        currentUser: FirebaseUser?, onSuccessful: ((String) -> Unit), onFailure: ((String) -> Unit)
    ) {
        currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken: String? = task.result?.token

                if (idToken != null) {
                    onSuccessful.invoke(idToken)
                }
            } else {
                onFailure(task.exception?.message ?: "")
            }
        }
    }

    fun logoutGoogle(fetchRemote: FetchRemote) {
        handler.postDelayed(timeoutRunnable, timeoutDuration)

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            auth.signOut()

            auth.addAuthStateListener { user ->
                handler.removeCallbacks(timeoutRunnable)

                if (user.currentUser == null) {
                    fetchRemote.onDataRetrieved?.invoke(false)
                } else {
                    fetchRemote.onCancelled?.invoke(OPERATION_LOCAL_FAILED)
                }
            }
        } else fetchRemote.onDataRetrieved?.invoke(false)
    }

    @SuppressLint("HardwareIds")
    private fun getGuestUniqueName(context: Context): String =
        "guest${Build.MODEL}${Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )}"

    private fun getTokenFromGuest(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val byteArray = md.digest(input.toByteArray())

        // Convert the byte array to a hexadecimal string
        val number = BigInteger(1, byteArray)
        var md5String = number.toString(16)

        // Pad with zeros to ensure a consistent length
        while (md5String.length < 32) {
            md5String = "0$md5String"
        }

        return md5String.shuffle()
    }

    private fun handleRTO(fetchRemote: FetchRemote) {
        timeoutRunnable = Runnable {
            fetchRemote.onCancelled?.invoke(Constant.ErrorType.REQUEST_TIME_OUT)
        }

        handler.postDelayed(timeoutRunnable, timeoutDuration)
    }

    companion object {
        @Volatile
        private var instance: FirebaseRemote? = null
        fun getInstance(): FirebaseRemote =
            instance ?: synchronized(this) {
                instance ?: FirebaseRemote()
            }.also { instance = it }
    }
}
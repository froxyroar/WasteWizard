package com.kelompok3.wastewizard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class homeActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = FirebaseAuth.getInstance()
        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")
        findViewById<TextView>(R.id.textView).text = email + "\n" + displayName
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        findViewById<Button>(R.id.btn_logout).setOnClickListener{
            auth.signOut()
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                mGoogleSignInClient.revokeAccess().addOnCompleteListener(this) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

}
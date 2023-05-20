package com.kelompok3.wastewizard

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val switch = view.findViewById<SwitchCompat>(R.id.switch_btn)
        val sharedPreferences = requireContext().getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night", false)

        if(nightMode){
            switch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switch.setOnCheckedChangeListener{ _, isChecked ->
            if(!isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("night", false)
                editor.apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("night", true)
                editor.apply()
            }
            activity?.recreate()
        }

        auth = FirebaseAuth.getInstance()
        mGoogleSignInClient =
            GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        btnLogout.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            auth.signOut()
            mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
                mGoogleSignInClient.revokeAccess().addOnCompleteListener(requireActivity()) {
                    startActivity(intent)
                }
            }
        }
        return view
    }
}
